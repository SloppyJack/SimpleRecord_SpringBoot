package cn.jackbin.SimpleRecord.common.config.datasource.method;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.common.config.datasource.method
 * @date: 2021/8/5 19:57
 **/
public enum  MethodConstant {
    SELECT_ONE_WITHOUT_LOGIC_DEL("selectOneWithoutLogicDel", "查询满足条件一条数据（无逻辑删除条件限制）", "<script>%s SELECT %s FROM %s %s %s\n</script>"),
    ;

    private final String method;
    private final String desc;
    private final String sql;

    MethodConstant(String method, String desc, String sql) {
        this.method = method;
        this.desc = desc;
        this.sql = sql;
    }

    public String getMethod() {
        return method;
    }

    public String getDesc() {
        return desc;
    }

    public String getSql() {
        return sql;
    }
}
