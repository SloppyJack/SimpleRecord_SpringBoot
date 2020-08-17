package cn.jackbin.SimpleRecord.service;

import cn.jackbin.SimpleRecord.entity.UserGroupDO;
import cn.jackbin.SimpleRecord.vo.UserGroupVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jackbin
 * @since 2020-07-21
 */
public interface UserGroupService extends IService<UserGroupDO> {
    /**
     * 根据用户Id获取用户组的视图
     * @param userId
     * @return
     */
    UserGroupVO getUserGroupVOByUserId(Long userId);
}
