package cn.jackbin.SimpleRecord.common.config.datasource.method;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.common.config.datasource.method
 * @date: 2021/8/16 19:31
 **/
public class LogicDelWithFillStatus extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        // fixed this sql fragment
        String sqlSet = "SET status=#{status},delete_time=now()";
        String sql = String.format(MethodConstant.DEL_BY_ID_WITH_FILL_STATUS.getSql(), tableInfo.getTableName(), sqlSet, tableInfo.getKeyColumn(),
                tableInfo.getKeyProperty(), tableInfo.getLogicDeleteSql(true, true));
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return addUpdateMappedStatement(mapperClass, modelClass, MethodConstant.DEL_BY_ID_WITH_FILL_STATUS.getMethod(), sqlSource);
    }
}
