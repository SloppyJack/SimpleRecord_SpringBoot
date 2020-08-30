package cn.jackbin.SimpleRecord.common.ioc;

import java.lang.annotation.*;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 此类已废弃，在SpringSecurity中已做了处理（注：保留仅供学习AOP）
 * @date: 2020/7/28 20:02
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginRequired {
}
