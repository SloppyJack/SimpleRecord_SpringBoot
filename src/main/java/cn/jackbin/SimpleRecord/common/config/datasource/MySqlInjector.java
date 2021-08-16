package cn.jackbin.SimpleRecord.common.config.datasource;

import cn.jackbin.SimpleRecord.common.config.datasource.method.LogicDelWithFillStatus;
import cn.jackbin.SimpleRecord.common.config.datasource.method.SelectOneWithoutLogicDel;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;

import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.common.config.datasource
 * @date: 2021/8/5 19:48
 **/
public class MySqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        methodList.add(new SelectOneWithoutLogicDel());
        methodList.add(new LogicDelWithFillStatus());
        return methodList;
    }
}
