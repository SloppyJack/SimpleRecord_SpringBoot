package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.entity.*;
import cn.jackbin.SimpleRecord.mapper.PermissionMapper;
import cn.jackbin.SimpleRecord.service.*;
import cn.jackbin.SimpleRecord.vo.UserPermissionVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionDO> implements PermissionService {
    @Autowired
    private PermissionMapper mapper;

    @Override
    public List<PermissionDO> getUserPermissions(Long userId) {
        return mapper.queryPermissionByUserId(userId);
    }
}
