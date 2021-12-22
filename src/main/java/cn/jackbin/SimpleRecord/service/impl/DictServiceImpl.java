package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.constant.CommonConstants;
import cn.jackbin.SimpleRecord.entity.DictDO;
import cn.jackbin.SimpleRecord.mapper.DictMapper;
import cn.jackbin.SimpleRecord.service.DictService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.service.impl
 * @date: 2021/7/20 20:07
 **/
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, DictDO> implements DictService {
    @Autowired
    private DictMapper dictMapper;

    @Override
    public void getByPage(String name, String code, Integer status, PageBO<DictDO> pageBO) {
        IPage<DictDO> page = new Page<>(pageBO.getPageNo(), pageBO.getPageSize());
        QueryWrapper<DictDO> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNoneBlank(name)) {
            queryWrapper.like("name", name);
        }
        if (StringUtils.isNoneBlank(code)) {
            queryWrapper.like("code", code);
        }
        if (status != null) {
            queryWrapper.eq("status", status);
        }
        queryWrapper.orderByAsc("order_no");
        page = dictMapper.selectPageWithoutLogicDel(page, queryWrapper);
        pageBO.setTotal((int) page.getTotal());
        pageBO.setList(page.getRecords());
    }

    @Override
    public void add(String name, String code, Integer orderNo, String remark) {
        DictDO dictDO = new DictDO();
        dictDO.setName(name);
        dictDO.setCode(code);
        dictDO.setRemark(remark);
        dictDO.setStatus(CommonConstants.STATUS_NORMAL);
        dictDO.setOrderNo(orderNo);
        dictDO.setIsSysDefault(CommonConstants.NOT_SYS_DEFAULT);
        dictMapper.insert(dictDO);
    }

    @Override
    public void edit(Integer id, String name, String code, Integer orderNo, String remark) {
        DictDO dictDO = new DictDO();
        dictDO.setId(Long.valueOf(id));
        dictDO.setName(name);
        dictDO.setCode(code);
        dictDO.setRemark(remark);
        dictDO.setOrderNo(orderNo);
        dictMapper.updateById(dictDO);
    }

    @Override
    public DictDO getByCode(String code) {
        QueryWrapper<DictDO> queryWrapper = new QueryWrapper<>();
        DictDO dictDO = new DictDO();
        dictDO.setCode(code);
        queryWrapper.eq("code", code);
        return dictMapper.selectOneWithoutLogicDel(queryWrapper);
    }

    @Transactional
    @Override
    public boolean removeById(Long id) {
        return dictMapper.delByIdFillStatus(id) > 0;
    }

    @Override
    public void reset(Integer id) {
        DictDO dictDO = new DictDO();
        dictDO.setId(Long.valueOf(id));
        dictDO.setStatus(CommonConstants.STATUS_NORMAL);
        dictDO.setDeleteTime(null);
        dictMapper.updateByIdWithoutLogicDel(dictDO);
    }
}
