package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.bo.PageBO;
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
        IPage<DictDO> page = new Page<>(pageBO.beginPosition(), pageBO.getPageSize());
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
        page = dictMapper.selectPage(page, queryWrapper);
        pageBO.setTotal((int) page.getTotal());
        pageBO.setList(page.getRecords());
    }
}
