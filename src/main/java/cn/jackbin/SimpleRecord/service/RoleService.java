package cn.jackbin.SimpleRecord.service;

import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.entity.RoleDO;
import cn.jackbin.SimpleRecord.vo.EditRoleVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
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

    PageBO<RoleDO> getByPage(String name, Boolean deleted, Date date, int pageIndex, int pageSize);

    boolean add(String name, String info);

    void editRole(Long id, String name, String info, List<Integer> menuIds);
}
