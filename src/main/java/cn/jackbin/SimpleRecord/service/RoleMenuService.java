package cn.jackbin.SimpleRecord.service;

import cn.jackbin.SimpleRecord.entity.RoleMenuDO;
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
public interface RoleMenuService extends IService<RoleMenuDO> {
    List<RoleMenuDO> getByRoleId(int id);

    /**
     * 删除角色权限
     */
    void removeByRoleId(Integer roleId);
}
