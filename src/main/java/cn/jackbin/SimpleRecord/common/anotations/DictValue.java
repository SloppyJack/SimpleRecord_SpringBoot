package cn.jackbin.SimpleRecord.common.anotations;

import java.lang.annotation.*;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 字典注解
 * @date: 2021/8/30 19:42
 **/

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface DictValue {

    String code();  // 默认值为fieldName

}
