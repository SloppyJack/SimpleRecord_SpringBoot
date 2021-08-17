package cn.jackbin.SimpleRecord.common.config.datasource.method;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.common.config.datasource.method
 * @date: 2021/8/17 23:02
 **/
public class SelectPageWithoutLogicDel extends AbstractMethodWrapper {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql = String.format(MethodConstant.SELECT_PAGE_WITHOUT_LOGIC_DEL.getSql(), sqlFirst(),
                sqlSelectColumns(tableInfo, true),
                tableInfo.getTableName(),
                sqlWhereEntityWrapperWithoutLogicDel(true, tableInfo),
                sqlComment());
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addSelectMappedStatementForTable(mapperClass, MethodConstant.SELECT_PAGE_WITHOUT_LOGIC_DEL.getMethod(), sqlSource, tableInfo);
    }
}
