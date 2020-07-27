package cn.jackbin.jianzhang.service.impl;

import cn.jackbin.jianzhang.constant.IdentityConstant;
import cn.jackbin.jianzhang.entity.UserIdentityDO;
import cn.jackbin.jianzhang.mapper.UserIdentityMapper;
import cn.jackbin.jianzhang.service.UserIdentityService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class UserIdentityServiceImpl extends ServiceImpl<UserIdentityMapper, UserIdentityDO> implements UserIdentityService {

    @Override
    public boolean verifyUsernamePassword(Long userId, String username, String password) {
        QueryWrapper<UserIdentityDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserIdentityDO::getUserId, userId)
                .eq(UserIdentityDO::getIdentityType, IdentityConstant.USERNAME_PASSWORD_IDENTITY);
        UserIdentityDO userIdentity = this.baseMapper.selectOne(wrapper);
        // todo 此处完成校验密码
        return false;
    }
}
