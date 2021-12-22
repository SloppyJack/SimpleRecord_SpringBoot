package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.constant.CommonConstants;
import cn.jackbin.SimpleRecord.entity.DictItemDO;
import cn.jackbin.SimpleRecord.mapper.DictItemMapper;
import cn.jackbin.SimpleRecord.service.DictItemService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.service.impl
 * @date: 2021/8/4 20:08
 **/
@Service
public class DictItemServiceImpl extends ServiceImpl<DictItemMapper, DictItemDO> implements DictItemService {
    @Autowired
    private DictItemMapper dictItemMapper;

    @Override
    public List<DictItemDO> getDictItemsByDictId(Integer dictId) {
        QueryWrapper<DictItemDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_id", dictId);
        queryWrapper.orderByAsc("order_no");
        return dictItemMapper.selectList(queryWrapper);
    }

    @Override
    public void getByPage(Integer dictId, PageBO<DictItemDO> pageBO) {
        IPage<DictItemDO> page = new Page<>(pageBO.getPageNo(), pageBO.getPageSize());
        QueryWrapper<DictItemDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_id", dictId);
        queryWrapper.orderByAsc("order_no");
        page = dictItemMapper.selectPage(page, queryWrapper);
        pageBO.setTotal((int) page.getTotal());
        pageBO.setList(page.getRecords());
    }

    @Override
    public void add(Integer dictId, String text, String value, Integer orderNo, String remark) {
        DictItemDO dictItemDO = new DictItemDO();
        dictItemDO.setDictId(dictId);
        dictItemDO.setText(text);
        dictItemDO.setValue(value);
        dictItemDO.setOrderNo(orderNo);
        dictItemDO.setRemark(remark);
        dictItemDO.setStatus(CommonConstants.STATUS_NORMAL);
        dictItemMapper.insert(dictItemDO);
    }

    @Override
    public void edit(Integer id, String text, String value, Integer orderNo, String remark) {
        DictItemDO dictItemDO = new DictItemDO();
        dictItemDO.setId(Long.valueOf(id));
        dictItemDO.setText(text);
        dictItemDO.setValue(value);
        dictItemDO.setOrderNo(orderNo);
        dictItemDO.setRemark(remark);
        dictItemMapper.updateById(dictItemDO);
    }

    @Override
    public void removeById(Long id) {
        dictItemMapper.delById(id);
    }

    @Override
    public DictItemDO getByValue(Integer dictId, String value) {
        QueryWrapper<DictItemDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_id", dictId);
        queryWrapper.eq("value", value);
        return dictItemMapper.selectOne(queryWrapper);
    }
}
