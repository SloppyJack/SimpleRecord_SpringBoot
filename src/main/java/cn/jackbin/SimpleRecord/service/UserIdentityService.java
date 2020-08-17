package cn.jackbin.SimpleRecord.service;

import cn.jackbin.SimpleRecord.entity.UserGroupDO;
import cn.jackbin.SimpleRecord.entity.UserIdentityDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jackbin
 * @since 2020-07-21
 */
public interface UserIdentityService extends IService<UserIdentityDO> {
    /**
     *@description: 校验用户
     *@createTime: 2020/7/27 21:35
     *@author: edit by bin
     */
    boolean verifyUsernamePassword(Long userId, String username, String password);
}
