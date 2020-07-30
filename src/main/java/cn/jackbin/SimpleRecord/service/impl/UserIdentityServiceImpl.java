package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.constant.IdentityConstant;
import cn.jackbin.SimpleRecord.entity.UserIdentityDO;
import cn.jackbin.SimpleRecord.mapper.UserIdentityMapper;
import cn.jackbin.SimpleRecord.service.UserIdentityService;
import cn.jackbin.SimpleRecord.util.EncryptUtil;
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
        String temp = EncryptUtil.getInstance().MD5(password);
        return userIdentity.getCredential().equals(temp);
    }
}
