package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.constant.CommonConstants;
import cn.jackbin.SimpleRecord.constant.RecordConstant;
import cn.jackbin.SimpleRecord.entity.RecordBookDO;
import cn.jackbin.SimpleRecord.mapper.RecordBookMapper;
import cn.jackbin.SimpleRecord.service.RecordBookService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.service.impl
 * @date: 2021/7/13 22:04
 **/
@Service
public class RecordBookServiceImpl extends ServiceImpl<RecordBookMapper, RecordBookDO> implements RecordBookService {
    @Autowired
    private RecordBookMapper recordBookMapper;

    @Override
    public void getByPage(Integer userId, PageBO<RecordBookDO> pageBO) {
        IPage<RecordBookDO> page = new Page<>(pageBO.beginPosition(), pageBO.getPageSize());
        QueryWrapper<RecordBookDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByAsc("order_no");
        recordBookMapper.selectPage(page, queryWrapper);
        pageBO.setTotal((int) page.getTotal());
        pageBO.setList(page.getRecords());
    }

    @Override
    public void add(Integer userId, String name, String remark, Integer orderNo) {
        RecordBookDO recordBookDO = new RecordBookDO();
        recordBookDO.setUserId(userId);
        recordBookDO.setName(name);
        recordBookDO.setRemark(remark);
        recordBookDO.setOrderNo(orderNo);
        recordBookDO.setStatus(CommonConstants.STATUS_NORMAL);
        // 新增的账本为非用户默认
        recordBookDO.setIsUserDefault(RecordConstant.NOT_USER_DEFAULT);
        recordBookMapper.insert(recordBookDO);
    }

    @Override
    public void getByName(String name) {
        QueryWrapper<RecordBookDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        recordBookMapper.selectOne(queryWrapper);
    }

    @Override
    public RecordBookDO getDefaultBook(Integer userId) {
        QueryWrapper<RecordBookDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_user_default", RecordConstant.USER_DEFAULT);
        queryWrapper.eq("user_id", userId);
        return recordBookMapper.selectOne(queryWrapper);
    }

    @Override
    public void edit(Long id, Integer userId, String name, String remark, Integer orderNo, Integer isUserDefault) {
        RecordBookDO recordBookDO = RecordBookDO.builder().id(id).userId(userId).
                name(name).remark(remark).orderNo(orderNo).isUserDefault(isUserDefault).build();
        recordBookMapper.updateById(recordBookDO);
    }

    @Transactional
    @Override
    public void updateDefault(Long defaultId, Long sourceId, Integer userId, String name, String remark, Integer orderNo, Integer isUserDefault) {
        // 取消原有默认账单
        RecordBookDO defaultBookDO = RecordBookDO.builder().id(defaultId).isUserDefault(RecordConstant.NOT_USER_DEFAULT).build();
        recordBookMapper.updateById(defaultBookDO);
        // 更新目标账单
        RecordBookDO recordBookDO = RecordBookDO.builder().id(sourceId).userId(userId).
                name(name).remark(remark).orderNo(orderNo).isUserDefault(isUserDefault).build();
        recordBookMapper.updateById(recordBookDO);
    }

    @Override
    public List<RecordBookDO> getList(Integer userId) {
        QueryWrapper<RecordBookDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByAsc("order_no");
        return recordBookMapper.selectList(queryWrapper);
    }
}
