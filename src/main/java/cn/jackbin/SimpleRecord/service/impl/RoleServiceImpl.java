package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.entity.RoleDO;
import cn.jackbin.SimpleRecord.mapper.RoleMapper;
import cn.jackbin.SimpleRecord.mapper.RoleMenuMapper;
import cn.jackbin.SimpleRecord.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleDO> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<RoleDO> getByUserId(Long userId) {
        return roleMapper.queryByUserId(userId);
    }

    @Override
    public List<RoleDO> getList(int pageIndex, int pageSize) {
        IPage<RoleDO> page = new Page<>(pageIndex, pageSize);//参数一是当前页，参数二是每页个数
//        return roleMapper.selectPage(page, null).getRecords();
        return roleMapper.selectList(null);
    }
}
