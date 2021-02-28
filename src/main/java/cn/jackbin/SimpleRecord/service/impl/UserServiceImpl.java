package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.mapper.UserMapper;
import cn.jackbin.SimpleRecord.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public PageBO<UserDO> getByPage(String username, Boolean deleted, Date date, int pageIndex, int pageSize) {
        int total = userMapper.queryTotal(username, deleted, date);
        List<UserDO> list = userMapper.queryByPage(username, deleted, date, pageIndex * pageSize, pageSize);
        return new PageBO<>(list, total);
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
}
