package cn.jackbin.SimpleRecord.service;

import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.entity.UserGroupDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jackbin
 * @since 2020-07-21
 */
public interface UserService extends IService<UserDO> {

    UserDO getUserByUserName(String userName);
}
