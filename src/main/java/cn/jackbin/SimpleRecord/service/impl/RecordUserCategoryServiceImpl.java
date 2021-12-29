package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.common.anotations.HandleDict;
import cn.jackbin.SimpleRecord.constant.RecordConstant;
import cn.jackbin.SimpleRecord.entity.DictDO;
import cn.jackbin.SimpleRecord.entity.DictItemDO;
import cn.jackbin.SimpleRecord.entity.RecordSysCategoryDO;
import cn.jackbin.SimpleRecord.entity.RecordUserCategoryDO;
import cn.jackbin.SimpleRecord.mapper.RecordUserCategoryMapper;
import cn.jackbin.SimpleRecord.service.DictItemService;
import cn.jackbin.SimpleRecord.service.DictService;
import cn.jackbin.SimpleRecord.service.RecordSysCategoryService;
import cn.jackbin.SimpleRecord.service.RecordUserCategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Autowired
    private DictItemService dictItemService;
    @Autowired
    private DictService dictService;
    @Autowired
    private RecordSysCategoryService recordSysCategoryService;

    @HandleDict
    @Override
    public List<RecordUserCategoryDO> getList(Integer userId) {
        QueryWrapper<RecordUserCategoryDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByAsc("type", "order_no");
        return userCategoryMapper.selectList(queryWrapper);
    }

    @Override
    public void getByPage(Integer userId, String recordTypeCode, PageBO<RecordUserCategoryDO> pageBO) {
        IPage<RecordUserCategoryDO> page = new Page<>(pageBO.getPageNo(), pageBO.getPageSize());
        DictDO dictDO = dictService.getByCode(RecordConstant.RECORD_TYPE);
        QueryWrapper<RecordUserCategoryDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        if (StringUtils.isNoneBlank(recordTypeCode)){
            DictItemDO dictItemDO = dictItemService.getByValue(dictDO.getId().intValue(), recordTypeCode);
            queryWrapper.eq("type", dictItemDO.getId());
        } else {    // 限制为支出或收入类别
            DictItemDO expendDictItemDO = dictItemService.getByValue(dictDO.getId().intValue(), RecordConstant.EXPEND_RECORD_TYPE);
            DictItemDO incomeDictItemDO = dictItemService.getByValue(dictDO.getId().intValue(), RecordConstant.INCOME_RECORD_TYPE);
            queryWrapper.in("type", expendDictItemDO.getId().intValue(), incomeDictItemDO.getId().intValue());
        }
        queryWrapper.orderByAsc("type", "order_no");
        page = userCategoryMapper.selectPage(page, queryWrapper);
        pageBO.setTotal((int) page.getTotal());
        pageBO.setList(page.getRecords());
    }

    @Transactional
    @Override
    public void reset(Integer userId) {
        // 1. 删除所有的用户类别
        QueryWrapper<RecordUserCategoryDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        userCategoryMapper.delete(queryWrapper);
        // 2. 初始化用户类别
        List<RecordSysCategoryDO> list = recordSysCategoryService.list();
        List<RecordUserCategoryDO> ret = new ArrayList<>();
        list.forEach(n -> {
            RecordUserCategoryDO temp = new RecordUserCategoryDO();
            temp.setUserId(userId);
            temp.setType(n.getType());
            temp.setName(n.getName());
            temp.setCode(n.getKey());
            temp.setOrderNo(n.getOrderNo());
        });
        saveBatch(ret); // 批量保存
    }

    @Override
    public void saveOrUpdate(Long id, Integer userId, String recordTypeCode, String name, Integer orderNo) {
        DictDO dictDO = dictService.getByCode(RecordConstant.RECORD_TYPE);
        DictItemDO dictItemDO = dictItemService.getByValue(dictDO.getId().intValue(), recordTypeCode);
        RecordUserCategoryDO recordUserCategoryDO = new RecordUserCategoryDO();
        if (id != null){
            recordUserCategoryDO.setId(id);
        }
        recordUserCategoryDO.setUserId(userId);
        recordUserCategoryDO.setType(dictItemDO.getId().intValue());
        recordUserCategoryDO.setName(name);
        recordUserCategoryDO.setOrderNo(orderNo);
        saveOrUpdate(recordUserCategoryDO);
    }
}
