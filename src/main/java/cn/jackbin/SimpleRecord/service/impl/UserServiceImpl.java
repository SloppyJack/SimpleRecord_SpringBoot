package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.mapper.UserMapper;
import cn.jackbin.SimpleRecord.service.UserService;
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
    public List<UserDO> getByPage(int pageIndex, int pageSize) {
        IPage<UserDO> page = new Page<>(pageIndex, pageSize);//参数一是当前页，参数二是每页个数
        page = userMapper.selectPage(page, null);
        return page.getRecords();
    }
}
