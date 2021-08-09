package cn.jackbin.SimpleRecord.common.config.datasource.method;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 查询某个值，排除逻辑删除字段
 * @date: 2021/8/5 19:54
 **/
public class SelectOneWithoutLogicDel extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        MethodConstant sqlMethod = MethodConstant.SELECT_ONE_WITHOUT_LOGIC_DEL;
        // 构建sql
        String sql = String.format(sqlMethod.getSql(), sqlFirst(),
                sqlSelectColumns(tableInfo, true),
                tableInfo.getTableName(),
                sqlWhereEntityWrapperWithoutLogicDel(true, tableInfo), sqlComment()
        );
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addSelectMappedStatementForTable(mapperClass, sqlMethod.getMethod(), sqlSource, tableInfo);
    }

    protected String sqlWhereEntityWrapperWithoutLogicDel(boolean newLine, TableInfo table) {
        String sqlScript = table.getAllSqlWhere(false, true, WRAPPER_ENTITY_DOT);
        sqlScript = SqlScriptUtils.convertIf(sqlScript, String.format("%s != null", WRAPPER_ENTITY), true);
        sqlScript += NEWLINE;
        sqlScript += SqlScriptUtils.convertIf(String.format(SqlScriptUtils.convertIf(" AND", String.format("%s and %s", WRAPPER_NONEMPTYOFENTITY, WRAPPER_NONEMPTYOFNORMAL), false) + " ${%s}", WRAPPER_SQLSEGMENT),
                String.format("%s != null and %s != '' and %s", WRAPPER_SQLSEGMENT, WRAPPER_SQLSEGMENT,
                        WRAPPER_NONEMPTYOFWHERE), true);
        sqlScript = SqlScriptUtils.convertWhere(sqlScript) + NEWLINE;
        sqlScript += SqlScriptUtils.convertIf(String.format(" ${%s}", WRAPPER_SQLSEGMENT),
                String.format("%s != null and %s != '' and %s", WRAPPER_SQLSEGMENT, WRAPPER_SQLSEGMENT,
                        WRAPPER_EMPTYOFWHERE), true);
        sqlScript = SqlScriptUtils.convertIf(sqlScript, String.format("%s != null", WRAPPER), true);
        return newLine ? NEWLINE + sqlScript : sqlScript;
    }
}
