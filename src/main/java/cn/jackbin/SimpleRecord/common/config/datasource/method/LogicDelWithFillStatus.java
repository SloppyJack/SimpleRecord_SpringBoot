package cn.jackbin.SimpleRecord.common.config.datasource.method;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 逻辑删除自动填充status
 * @date: 2021/8/16 19:31
 **/
public class LogicDelWithFillStatus extends AbstractMethodWrapper {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        // fixed this sql fragment
        String sqlSet = "SET status=1,delete_time=now()";
        String sql = String.format(MethodConstant.DEL_BY_ID_WITH_FILL_STATUS.getSql(), tableInfo.getTableName(), sqlSet, tableInfo.getKeyColumn(),
                tableInfo.getKeyProperty(), tableInfo.getLogicDeleteSql(true, true));
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return addUpdateMappedStatement(mapperClass, modelClass, MethodConstant.DEL_BY_ID_WITH_FILL_STATUS.getMethod(), sqlSource);
    }
}
