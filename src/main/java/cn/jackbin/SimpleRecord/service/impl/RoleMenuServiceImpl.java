package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.entity.RoleMenuDO;
import cn.jackbin.SimpleRecord.mapper.RoleMenuMapper;
import cn.jackbin.SimpleRecord.service.RoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
