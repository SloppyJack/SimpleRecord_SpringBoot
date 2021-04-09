package cn.jackbin.SimpleRecord.common.anotations;

import cn.jackbin.SimpleRecord.common.enums.BusinessType;

import java.lang.annotation.*;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 自定义操作日志记录注解
 * @date: 2021/4/3 15:14
 **/
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CommonLog {
    /**
     * 模块
     */
    String title() default "";

    /**
     * 业务
     */
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;
}
