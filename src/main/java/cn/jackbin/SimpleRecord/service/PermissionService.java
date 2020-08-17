package cn.jackbin.SimpleRecord.service;

import cn.jackbin.SimpleRecord.entity.PermissionDO;
import cn.jackbin.SimpleRecord.vo.UserPermissionVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jackbin
 * @since 2020-07-21
 */
public interface PermissionService extends IService<PermissionDO> {
    UserPermissionVO getUserPermissionVOByUserId(Long userId);
}
