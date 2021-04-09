package cn.jackbin.SimpleRecord.common.anotations;

import java.lang.annotation.*;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 登录校验
 * @date: 2020/7/28 20:02
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginRequired {
}
