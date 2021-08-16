package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.bo.PageBO;
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
        return dictItemMapper.selectList(queryWrapper);
    }

    @Override
    public void getByPage(Integer dictId, PageBO<DictItemDO> pageBO) {
        IPage<DictItemDO> page = new Page<>(pageBO.beginPosition(), pageBO.getPageSize());
        QueryWrapper<DictItemDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_id", dictId);
        page = dictItemMapper.selectPage(page, queryWrapper);
        pageBO.setTotal((int) page.getTotal());
        pageBO.setList(page.getRecords());
    }
}
