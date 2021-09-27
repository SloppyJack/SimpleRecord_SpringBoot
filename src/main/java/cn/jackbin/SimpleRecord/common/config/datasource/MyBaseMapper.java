package cn.jackbin.SimpleRecord.common.config.datasource;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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

    int delByIdFillStatus(Serializable id);

    /**
     * 根据 entity 条件，分页查询记录（无逻辑删除条件限制）
     *
     * @param page         分页查询条件（可以为 RowBounds.DEFAULT）
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    <E extends IPage<T>> E selectPageWithoutLogicDel(E page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    /**
     * 根据 ID 修改（无逻辑删除条件限制）
     *
     * @param entity 实体对象
     */
    int updateByIdWithoutLogicDel(@Param(Constants.ENTITY) T entity);

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     */
    int delById(Serializable id);
}
