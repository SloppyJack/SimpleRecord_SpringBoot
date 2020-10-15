package cn.jackbin.SimpleRecord.service;

import cn.jackbin.SimpleRecord.entity.PermissionDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jackbin
 * @since 2020-07-21
 */
public interface PermissionService extends IService<PermissionDO> {

    /**
     * 获取指定用户的权限
     */
    List<PermissionDO> getUserPermissions(Long userId);

    /**
     * 通过id查找指定的权限
     */
    PermissionDO getById(Long id);

    /**
     * 获取所有的权限
     */
    List<PermissionDO> getAll();

    /**
     * 分页获取权限
     */
    List<PermissionDO> getByPage(int pageIndex, int pageSize);
}
