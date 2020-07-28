package cn.jackbin.jianzhang.common.ioc;

import java.lang.annotation.*;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.jianzhang.common.ioc
 * @date: 2020/7/28 20:02
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginRequired {
}
