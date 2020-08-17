package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.entity.GroupPermissionDO;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.mapper.GroupPermissionMapper;
import cn.jackbin.SimpleRecord.service.GroupPermissionService;
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
public class GroupPermissionServiceImpl extends ServiceImpl<GroupPermissionMapper, GroupPermissionDO> implements GroupPermissionService {

    @Override
    public GroupPermissionDO getByGroupId(int id) {
        QueryWrapper<GroupPermissionDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("groupId",id);
        return getOne(queryWrapper);
    }
}
