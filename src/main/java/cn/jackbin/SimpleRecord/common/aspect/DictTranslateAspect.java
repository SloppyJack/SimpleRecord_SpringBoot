package cn.jackbin.SimpleRecord.common.aspect;

import cn.jackbin.SimpleRecord.common.anotations.DictValue;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.entity.DictDO;
import cn.jackbin.SimpleRecord.entity.DictItemDO;
import cn.jackbin.SimpleRecord.exception.BusinessException;
import cn.jackbin.SimpleRecord.service.DictItemService;
import cn.jackbin.SimpleRecord.service.DictService;
import cn.jackbin.SimpleRecord.utils.ClassUtil;
import cn.jackbin.SimpleRecord.vo.Result;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 翻译字典
 * @date: 2021/8/30 19:55
 **/
@Component
@Aspect
@Slf4j
public class DictTranslateAspect implements Ordered {
    private final int order = 101;
    Object ret;

    @Autowired
    private DictService dictService;
    @Autowired
    private DictItemService dictItemService;

    @Pointcut("@annotation(cn.jackbin.SimpleRecord.common.anotations.HandleDict)")
    public void doHandler() {

    }

    // 开始环绕
    @Around("doHandler()")
    public Object around(ProceedingJoinPoint joinPoint)throws Throwable {
        // 切面往下执行
        ret = joinPoint.proceed();
        // 处理返回结果
        if (ret instanceof Result) {
            Result<?> result = (Result<?>) ret;
            handleObj(result.getData());
        } else {
            handleObj(ret);
        }
        return ret;
    }

    /**
     * 处理普通的Object
     * @param obj
     * @return
     */
    private void handleObj(Object obj) {
        if (obj instanceof List) {
            List list = (List) obj;
            Object singleDO = list.get(0);
            Map<String, List<DictItemDO>> dictValues = getDictItems(singleDO);
            Map<String, String> handleFieldsName = getFields(singleDO);
            if (dictValues.size() < 1 && handleFieldsName.size() < 1) {
                return;
            }
            for (int i=0; i<list.size(); i++) {
                Object o = list.get(i);
                Object n = translateSingle(o, dictValues, handleFieldsName);
                list.set(i, n);
            }
        } else if (obj instanceof Map){
            log.warn("can't translate dict in type Map");
        } else {
            Map<String, List<DictItemDO>> dictValues = getDictItems(obj);
            Map<String, String> handleFieldsName = getFields(obj);
            ret = translateSingle(obj, dictValues, handleFieldsName);
        }
    }

    /**
     * get fields in DO
     * @param obj
     * @return fieldName -> dictCode
     */
    private Map<String, String> getFields(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        return Arrays.stream(fields).filter(n -> n.isAnnotationPresent(DictValue.class)).collect(
                Collectors.toMap(
                        Field::getName,
                        val -> val.getAnnotation(DictValue.class).code(),
                        (o, n) -> o
                )
        );
    }

    /**
     * find all dictValues
     */
    private Map<String, List<DictItemDO>> getDictItems(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        Map<String, List<DictItemDO>> dictValues = Arrays.stream(fields).filter(n -> n.isAnnotationPresent(DictValue.class)).collect(HashMap::new, (x, y) -> {
            x.put(y.getAnnotation(DictValue.class).code(), null); // key is code
        }, HashMap::putAll);
        for (String dictCode : dictValues.keySet()) {
            DictDO dictDO = dictService.getByCode(dictCode);
            if (dictDO == null) {
                log.warn("can't translate the code [{}], please check again", dictCode);
                continue;
            }
            List<DictItemDO> dictItemDOS = dictItemService.getDictItemsByDictId(dictDO.getId().intValue());
            dictValues.put(dictCode, dictItemDOS);
        }
        return dictValues;
    }

    /**
     * 处理单个实体类
     * @param obj
     */
    private Object translateSingle(Object obj, Map<String, List<DictItemDO>> dictValues, Map<String, String> fieldsName) {
        final String suffix = "Text";
        if (obj instanceof Iterable) {
            log.error("can't translate the obj with type of Iterable");
            throw new BusinessException(CodeMsg.SERVER_EXCEPTION);
        }
        Class<?> clazz = obj.getClass();
        Map<String, Class<?>> propertyMap = new HashMap<>();
        Map<String, String> valMap = new HashMap<>();
        for (String fieldName : fieldsName.keySet()) {
            try {
                Object fieldVal = ClassUtil.getProperty(clazz.getDeclaredField(fieldName), obj);
                if (fieldVal == null) {
                    log.warn("the field [{}] value is null, please check", fieldName);
                    continue;
                }
                String dictCode = fieldsName.get(fieldName);
                DictItemDO dictItemVal = dictValues.get(dictCode).stream().filter(n -> n.getId().toString().equals(fieldVal.toString())).findFirst().orElse(null);
                if (dictItemVal == null) {
                    log.warn("can't find the id [{}] in dictItem list", fieldVal);
                    continue;
                }
                propertyMap.put(fieldName + suffix, String.class); // put property
                valMap.put(fieldName + suffix, dictItemVal.getText());
            } catch (NoSuchFieldException e) {
                log.warn("field [{}] not in class [{}]", fieldName, obj.getClass());
            }
        }
        // dynamic add field
        Object target = generateBean(obj.getClass(), propertyMap);
        BeanMap beanMap = BeanMap.create(target);
        beanMap.putAll(valMap);
        beanMap.putAll(ClassUtil.bean2map(obj));   // add old values
        return target;
    }

    private Object generateBean(Class<?> superclass, Map<String, Class<?>> propertyMap) {
        BeanGenerator generator = new BeanGenerator();
        if (null != superclass) {
            generator.setSuperclass(superclass);
        }
        BeanGenerator.addProperties(generator, propertyMap);
        return generator.create();
    }


    @Override
    public int getOrder() {
        return order;
    }
}
