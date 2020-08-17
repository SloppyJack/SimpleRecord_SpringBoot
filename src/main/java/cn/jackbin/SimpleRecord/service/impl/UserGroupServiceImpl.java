package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.entity.GroupDO;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.entity.UserGroupDO;
import cn.jackbin.SimpleRecord.entity.UserIdentityDO;
import cn.jackbin.SimpleRecord.mapper.UserGroupMapper;
import cn.jackbin.SimpleRecord.service.GroupService;
import cn.jackbin.SimpleRecord.service.UserGroupService;
import cn.jackbin.SimpleRecord.service.UserIdentityService;
import cn.jackbin.SimpleRecord.service.UserService;
import cn.jackbin.SimpleRecord.vo.UserGroupVO;
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
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroupDO> implements UserGroupService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserIdentityService userIdentityService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserGroupService userGroupService;

    @Override
    public UserGroupVO getUserGroupVOByUserId(Long userId) {
        UserDO user = userService.getById(userId);
        UserIdentityDO userIdentityDO = userIdentityService.getById(userId);
        UserGroupDO userGroupDO = userGroupService.getById(userId);
        GroupDO groupDO = groupService.getById(userGroupDO.getGroupId());
        return new UserGroupVO(user, userIdentityDO, groupDO);
    }
}
