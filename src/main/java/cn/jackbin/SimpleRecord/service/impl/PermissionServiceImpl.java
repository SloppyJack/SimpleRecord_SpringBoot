package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.entity.*;
import cn.jackbin.SimpleRecord.mapper.PermissionMapper;
import cn.jackbin.SimpleRecord.service.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    @Override
    public PermissionDO getById(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<PermissionDO> getAll() {
        return mapper.selectList(null);
    }

    @Override
    public List<PermissionDO> getByPage(int pageIndex, int pageSize) {
        IPage<PermissionDO> userPage = new Page<>(pageIndex, pageSize);//参数一是当前页，参数二是每页个数
        userPage = mapper.selectPage(userPage, null);
        return userPage.getRecords();
    }
}
