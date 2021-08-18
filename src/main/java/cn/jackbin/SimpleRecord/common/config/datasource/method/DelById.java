package cn.jackbin.SimpleRecord.common.config.datasource.method;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.common.config.datasource.method
 * @date: 2021/8/18 21:33
 **/
public class DelById extends AbstractMethodWrapper {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql = String.format(MethodConstant.DEL_BY_ID.getSql(), tableInfo.getTableName(), tableInfo.getKeyColumn(),
                tableInfo.getKeyProperty());
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Object.class);
        return this.addDeleteMappedStatement(mapperClass, MethodConstant.DEL_BY_ID.getMethod(), sqlSource);
    }
}
