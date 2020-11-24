package cn.jackbin.SimpleRecord.service;

import cn.jackbin.SimpleRecord.entity.RoleDO;
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
public interface RoleService extends IService<RoleDO> {

    List<RoleDO> getByUserId(Long userId);
}
