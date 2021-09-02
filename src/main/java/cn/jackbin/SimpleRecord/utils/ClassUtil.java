package cn.jackbin.SimpleRecord.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.utils
 * @date: 2021/9/1 21:02
 **/
public class ClassUtil {
    /**
     * 获取属性值
     */
    public static Object getProperty(Field field, Object instance) {
        try {
            field.setAccessible(true);
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), instance.getClass());
            Method readMethod = pd.getReadMethod();
            return readMethod.invoke(instance);
        } catch (InvocationTargetException | IllegalAccessException | IntrospectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Bean转为Map
     * @param bean
     * @return
     * @throws Exception
     */
    public static Map<String,Object> bean2map(Object bean) {
        Map<String,Object> map = new HashMap<>();
        try {
            // 获取JavaBean的描述器
            BeanInfo b = Introspector.getBeanInfo(bean.getClass(),Object.class);
            //获取属性描述器
            PropertyDescriptor[] pds = b.getPropertyDescriptors();
            //对属性迭代
            for (PropertyDescriptor pd : pds) {
                //属性名称
                String propertyName = pd.getName();
                //属性值,用getter方法获取
                Method m = pd.getReadMethod();
                Object properValue = m.invoke(bean);//用对象执行getter方法获得属性值
                //把属性名-属性值 存到Map中
                map.put(propertyName, properValue);
            }
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return map;
    }
}
