package cn.jackbin.SimpleRecord.utils;

import java.util.UUID;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.utils
 * @date: 2021/4/26 23:38
 **/
public class IDUtil {
    /**
     * 32位不带"-"
     */
    public static String randomUUID(){
        // 生成36位带-uuid
        String uuidOld = UUID.randomUUID().toString();
        // 去掉-
        String uuid =
                (uuidOld.substring(0,8))    +
                        (uuidOld.substring(9,13))   +
                        (uuidOld.substring(14,18))  +
                        (uuidOld.substring(19,23))  +
                        (uuidOld.substring(24,36));
        return uuid;
    }
}
