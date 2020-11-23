package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.entity.RoleMenuDO;
import cn.jackbin.SimpleRecord.mapper.RoleMenuMapper;
import cn.jackbin.SimpleRecord.service.RoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    @Override
    public List<RoleMenuDO> getByRoleId(int id) {
        QueryWrapper<RoleMenuDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("groupId",id);
        return list(queryWrapper);
    }
}
