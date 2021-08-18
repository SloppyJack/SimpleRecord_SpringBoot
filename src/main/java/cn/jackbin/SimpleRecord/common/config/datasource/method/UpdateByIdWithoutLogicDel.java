package cn.jackbin.SimpleRecord.common.config.datasource.method;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.common.config.datasource.method
 * @date: 2021/8/18 19:48
 **/
public class UpdateByIdWithoutLogicDel extends AbstractMethodWrapper {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        boolean logicDelete = tableInfo.isLogicDelete();
        String sql = String.format(MethodConstant.UPDATE_BY_ID_WITHOUT_LOGIC_DEL.getSql(),
                tableInfo.getTableName(),
                sqlSet(logicDelete, false, tableInfo, false, ENTITY, ENTITY_DOT),
                tableInfo.getKeyColumn(), ENTITY_DOT + tableInfo.getKeyProperty());
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return addUpdateMappedStatement(mapperClass, modelClass, MethodConstant.UPDATE_BY_ID_WITHOUT_LOGIC_DEL.getMethod(), sqlSource);
    }
}
