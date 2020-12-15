package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.entity.MenuDO;
import cn.jackbin.SimpleRecord.mapper.MenuMapper;
import cn.jackbin.SimpleRecord.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.service.impl
 * @date: 2020/11/23 21:37
 **/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuDO> implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<MenuDO> getUserMenus(Long userId) {
        return menuMapper.queryUserMenus(userId);
    }

    @Override
    public MenuDO getById(Long id) {
        return null;
    }

    @Override
    public List<MenuDO> getAll() {
        return null;
    }

    @Override
    public List<MenuDO> getByPage(int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public List<MenuDO> getAllMenus() {
        return menuMapper.queryAllMenus();
    }

    @Override
    public List<MenuDO> getRoleMenus(Long roleId) {
        return menuMapper.queryRoleMenus(roleId);
    }
}
