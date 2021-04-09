package cn.jackbin.SimpleRecord.common.enums;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.common.enums
 * @date: 2021/4/7 22:37
 **/
public enum BusinessStatus {
    /**
     * 成功
     */
    SUCCESS(0),

    /**
     * 失败
     */
    FAIL(-1),
    ;

    private int code;

    BusinessStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
