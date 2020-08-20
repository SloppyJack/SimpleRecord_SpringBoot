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
    List<PermissionDO> getUserPermissions(Long userId);
}
