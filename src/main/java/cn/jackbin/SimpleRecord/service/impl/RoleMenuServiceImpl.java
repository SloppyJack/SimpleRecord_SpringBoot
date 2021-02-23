package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.entity.RoleMenuDO;
import cn.jackbin.SimpleRecord.mapper.RoleMenuMapper;
import cn.jackbin.SimpleRecord.service.RoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jackbin
 * @since 2020-07-21
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenuDO> implements RoleMenuService {
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<RoleMenuDO> getByRoleId(int id) {
        QueryWrapper<RoleMenuDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",id);
        return list(queryWrapper);
    }

    @Override
    public void removeByRoleId(Integer roleId) {
        QueryWrapper<RoleMenuDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        roleMenuMapper.delete(queryWrapper);
    }

    @Override
    public int removeByIds(List<Long> ids) {
        return roleMenuMapper.deleteBatch(ids);
    }

    @Transactional
    @Override
    public void edit(Integer roleId, List<Integer> menuIds) {
        // 查询该角色的所有的菜单权限
        List<RoleMenuDO> existedRDs = getByRoleId(roleId);
        Iterator<RoleMenuDO> iterator = existedRDs.iterator();
        while (iterator.hasNext()) {
            RoleMenuDO temp = iterator.next();
            if (menuIds.contains(temp.getMenuId())) {
                menuIds.remove(temp.getMenuId());
                iterator.remove();
            }
        }
        List<Long> delIds = new ArrayList<>();
        existedRDs.forEach(n -> delIds.add(n.getId()));
        // 删除菜单权限
        if (delIds.size() > 0) {
            removeByIds(delIds);
        }
        // 添加该角色的菜单权限
        List<RoleMenuDO> list = new ArrayList<>();
        for (Integer i : menuIds) {
            list.add(new RoleMenuDO(roleId, i));
        }
        saveBatch(list);
    }
}
