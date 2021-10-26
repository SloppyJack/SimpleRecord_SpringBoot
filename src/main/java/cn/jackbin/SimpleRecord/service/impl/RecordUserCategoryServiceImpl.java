package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.common.anotations.HandleDict;
import cn.jackbin.SimpleRecord.entity.RecordUserCategoryDO;
import cn.jackbin.SimpleRecord.mapper.RecordUserCategoryMapper;
import cn.jackbin.SimpleRecord.service.RecordUserCategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.service.impl
 * @date: 2021/7/13 22:04
 **/
@Service
public class RecordUserCategoryServiceImpl extends ServiceImpl<RecordUserCategoryMapper, RecordUserCategoryDO> implements RecordUserCategoryService {

    @Autowired
    private RecordUserCategoryMapper userCategoryMapper;

    @HandleDict
    @Override
    public List<RecordUserCategoryDO> getList(Integer userId) {
        QueryWrapper<RecordUserCategoryDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return userCategoryMapper.selectList(queryWrapper);
    }
}
