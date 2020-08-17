package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.entity.GroupPermissionDO;
import cn.jackbin.SimpleRecord.entity.PermissionDO;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.entity.UserGroupDO;
import cn.jackbin.SimpleRecord.mapper.PermissionMapper;
import cn.jackbin.SimpleRecord.service.GroupPermissionService;
import cn.jackbin.SimpleRecord.service.PermissionService;
import cn.jackbin.SimpleRecord.service.UserGroupService;
import cn.jackbin.SimpleRecord.service.UserService;
import cn.jackbin.SimpleRecord.vo.UserPermissionVO;
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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionDO> implements PermissionService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserGroupService userGroupService;
    @Autowired
    private GroupPermissionService groupPermissionService;
    @Autowired
    private PermissionService permissionService;

    @Override
    public UserPermissionVO getUserPermissionVOByUserId(Long userId) {
        UserDO userDO = userService.getById(userId);
        UserGroupDO userGroupDO = userGroupService.getById(userId);
        GroupPermissionDO groupPermissionDO = groupPermissionService.getByGroupId(userGroupDO.getGroupId());
        PermissionDO permissionDO = permissionService.getById(groupPermissionDO.getPermissionId());
        return new UserPermissionVO(userDO,permissionDO);
    }
}
