package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.entity.RoleDO;
import cn.jackbin.SimpleRecord.entity.RoleMenuDO;
import cn.jackbin.SimpleRecord.exception.BusinessException;
import cn.jackbin.SimpleRecord.mapper.RoleMapper;
import cn.jackbin.SimpleRecord.mapper.RoleMenuMapper;
import cn.jackbin.SimpleRecord.service.MenuService;
import cn.jackbin.SimpleRecord.service.RoleMenuService;
import cn.jackbin.SimpleRecord.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jackbin
 * @since 2020-07-21
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleDO> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<RoleDO> getByUserId(Long userId) {
        return roleMapper.queryByUserId(userId);
    }

    @Override
    public List<RoleDO> getRoles() {
        return roleMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public PageBO<RoleDO> getByPage(String name, Boolean deleted, Date date, int pageIndex, int pageSize) {
        int total = roleMapper.queryTotal(name, deleted, date);
        List<RoleDO> list = roleMapper.queryByPage(name, deleted, date, pageIndex * pageSize, pageSize);
        return new PageBO<>(list, total);
    }

    @Override
    public boolean add(String name, String info) {
        RoleDO roleDO = new RoleDO();
        roleDO.setName(name);
        roleDO.setInfo(info);
        return roleMapper.insert(roleDO) > 0;
    }

    @Transactional
    @Override
    public void edit(Long id, String name, String info, List<Integer> menuIds) {
        QueryWrapper<RoleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("id", id);
        queryWrapper.eq("name", name);
        if (roleMapper.selectOne(queryWrapper) != null) {
            throw new BusinessException(CodeMsg.ROLE_NAME_EXIST);
        }
        RoleDO roleDO = new RoleDO(id, name, info);
        roleMapper.updateById(roleDO);
        if (menuIds.size() != 0) {
            roleMenuService.edit(id.intValue(), menuIds);
        }
    }

    @Override
    public void reset(Integer id) {
        roleMapper.notDelete(Long.valueOf(id));
    }

    @Override
    public RoleDO getByName(String name) {
        QueryWrapper<RoleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        return roleMapper.selectOne(queryWrapper);
    }
}
