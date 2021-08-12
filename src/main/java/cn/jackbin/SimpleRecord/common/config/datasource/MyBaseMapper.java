package cn.jackbin.SimpleRecord.common.config.datasource;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 自定义通用mapper
 * @date: 2021/8/5 19:50
 **/
public interface MyBaseMapper<T> extends BaseMapper<T> {

    T selectOneWithoutLogicDel(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    int logicDelByIdFillStatus(Serializable id);

    int deleteByIdWithFill(T t);
}
