package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.entity.RoleDO;
import cn.jackbin.SimpleRecord.entity.UserRoleDO;
import cn.jackbin.SimpleRecord.mapper.UserRoleMapper;
import cn.jackbin.SimpleRecord.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleDO> implements UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Transactional
    @Override
    public void edit(Integer userId, List<Integer> roleIds) {
        // 删除用户的所有角色
        userRoleMapper.deleteByUserId(userId);
        // 更新
        List<UserRoleDO> list = new ArrayList<>();
        roleIds.forEach(n -> {
            UserRoleDO userRoleDO = new UserRoleDO();
            userRoleDO.setUserId(userId);
            userRoleDO.setRoleId(n);
            list.add(userRoleDO);
        });
        saveBatch(list);
    }
}
