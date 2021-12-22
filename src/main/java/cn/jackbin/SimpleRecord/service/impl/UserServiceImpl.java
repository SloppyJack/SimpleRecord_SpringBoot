package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.constant.RoleConstant;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.entity.UserRoleDO;
import cn.jackbin.SimpleRecord.mapper.UserMapper;
import cn.jackbin.SimpleRecord.service.RoleService;
import cn.jackbin.SimpleRecord.service.UserRoleService;
import cn.jackbin.SimpleRecord.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDO getByName(String userName) {
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userName);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public UserDO getById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<UserDO> getUsers() {
        return userMapper.selectList(null);
    }

    @Override
    public void getByPage(String username, Boolean deleted, Date date, PageBO<UserDO> pageBO) {
        int total = userMapper.queryTotal(username, deleted, date);
        List<UserDO> list = userMapper.queryByPage(username, deleted, date, pageBO.beginPosition(), pageBO.getPageSize());
        pageBO.setList(list);
        pageBO.setTotal(total);
    }

    @Transactional
    @Override
    public void edit(Integer id, String nickname, Integer sex, String email) {
        // 更新用户
        UserDO user = new UserDO();
        user.setId(id.longValue());
        user.setNickname(nickname);
        user.setSex(sex);
        user.setEmail(email);
        userMapper.updateById(user);
    }

    @Override
    public void add(String username, String nickname, Integer sex, String email, String credential) {
        UserDO user = new UserDO();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setSex(sex);
        user.setEmail(email);
        user.setCredential(new BCryptPasswordEncoder().encode(credential));
        save(user);
    }

    @Override
    public void reset(Integer id) {
        userMapper.notDelete(Long.valueOf(id));
    }

    @Transactional
    @Override
    public void saveWithDefaultRole(UserDO userDO) {
        // 保存用户
        save(userDO);
        // 找到默认角色Id
        Long roleId = roleService.getByName(RoleConstant.DEFAULT).getId();
        // 保存默认角色
        userRoleService.save(new UserRoleDO(userDO.getId().intValue(), roleId.intValue()));
    }
}
