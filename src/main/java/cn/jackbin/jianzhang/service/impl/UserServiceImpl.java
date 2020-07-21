package cn.jackbin.jianzhang.service.impl;

import cn.jackbin.jianzhang.entity.UserDO;
import cn.jackbin.jianzhang.mapper.UserMapper;
import cn.jackbin.jianzhang.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
