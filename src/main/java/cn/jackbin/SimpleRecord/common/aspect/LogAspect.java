package cn.jackbin.SimpleRecord.common.aspect;

import cn.jackbin.SimpleRecord.common.LocalUserId;
import cn.jackbin.SimpleRecord.common.anotations.CommonLog;
import cn.jackbin.SimpleRecord.common.enums.BusinessStatus;
import cn.jackbin.SimpleRecord.common.manager.AsyncManager;
import cn.jackbin.SimpleRecord.common.manager.factory.AsyncFactory;
import cn.jackbin.SimpleRecord.entity.CommonLogDO;
import cn.jackbin.SimpleRecord.utils.IpUtil;
import cn.jackbin.SimpleRecord.utils.ServletUtil;
import cn.jackbin.SimpleRecord.utils.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.common.aspect
 * @date: 2021/4/7 21:20
 **/
@Component
@Aspect
@Slf4j
public class LogAspect implements Ordered {
    // 执行顺序，越小越先执行（遵从同心圆的概念）
    private final int order = 100;

    /** 排除敏感属性字段 */
    public static final String[] EXCLUDE_PROPERTIES = { "password", "oldPassword", "newPassword", "confirmPassword" };

    @Pointcut("@annotation(cn.jackbin.SimpleRecord.common.anotations.CommonLog)")
    public void doHandler(){

    }

    /**
     * 处理请求后执行
     * @param joinPoint
     * @return
     */
    @AfterReturning(pointcut = "doHandler()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        handleLog(joinPoint, null, jsonResult);
    }

    @AfterThrowing(pointcut = "doHandler()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    /**
     * 处理日志
     * @param joinPoint
     * @param e
     * @param jsonResult
     */
    private void handleLog(final JoinPoint joinPoint, final Exception e, final Object jsonResult) {
        try {
            // 获得自定义注解
            CommonLog annotationLog = getAnnotationLog(joinPoint);
            if (annotationLog == null)
            {
                return;
            }

            // 获取当前用户Id
            Long userId = LocalUserId.get();
            // TODO 此功能需测试
            String ip = IpUtil.getIpAddr(ServletUtil.getRequest());
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            // 添加数据
            CommonLogDO logDO = new CommonLogDO();
            logDO.setTitle(annotationLog.title());
            logDO.setStatus(BusinessStatus.SUCCESS.getCode());
            logDO.setBusinessTypeCode(annotationLog.businessType().getCode());
            logDO.setBusinessTypeName(annotationLog.businessType().getName());
            logDO.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            logDO.setRequestMethod(ServletUtil.getRequest().getMethod());
            // 是否需要保存request，参数和值
            if (annotationLog.isSaveRequestData()) {
                // 获取参数的信息，传入到数据库中。
                setRequestValue(logDO);
            }
            logDO.setRequestUrl(ServletUtil.getRequest().getRequestURL().toString());
            logDO.setId(userId);
            logDO.setOperIp(ip);
            if (e != null)
            {
                logDO.setStatus(BusinessStatus.FAIL.getCode());
                logDO.setErrorMsg(StringUtil.substring(e.getMessage(), 0, 2000));
            }
            // 异步保存日志
            AsyncManager.me().execute(AsyncFactory.recordCommonLog(logDO));
        } catch (Exception exception) {
            log.error("日志记录异常，msg{}", exception.getMessage());
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private CommonLog getAnnotationLog(JoinPoint joinPoint) throws Exception
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null)
        {
            return method.getAnnotation(CommonLog.class);
        }
        return null;
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param commonLogDO 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(CommonLogDO commonLogDO) throws Exception
    {
        Map<String, String[]> map = ServletUtil.getRequest().getParameterMap();
        if (StringUtil.isNotEmpty(map))
        {
            PropertyPreFilters.MySimplePropertyPreFilter excludefilter = new PropertyPreFilters().addFilter();
            excludefilter.addExcludes(EXCLUDE_PROPERTIES);
            String params = JSONObject.toJSONString(map, excludefilter);
            commonLogDO.setRequestParam(StringUtil.substring(params, 0, 2000));
        }
    }

    @Override
    public int getOrder() {
        return order;
    }
}
