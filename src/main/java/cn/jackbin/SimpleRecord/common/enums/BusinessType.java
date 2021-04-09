package cn.jackbin.SimpleRecord.common.enums;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 业务类型
 * @date: 2021/4/7 21:14
 **/
public enum BusinessType {
    /**
     * 其它
     */
    OTHER("other", "其他"),

    QUERY("query", "查询"),

    /**
     * 新增
     */
    INSERT("insert", "新增"),

    /**
     * 修改
     */
    UPDATE("update", "修改"),

    /**
     * 删除
     */
    DELETE("del", "修改"),

    /**
     * 授权
     */
    GRANT("grant", "授权"),

    /**
     * 导出
     */
    EXPORT("exprot", "导出"),

    /**
     * 导入
     */
    IMPORT("import", "导入"),

    /**
     * 清空
     */
    CLEAN("clean", "清空"),
    ;
    private String code;
    private String name;

    BusinessType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
