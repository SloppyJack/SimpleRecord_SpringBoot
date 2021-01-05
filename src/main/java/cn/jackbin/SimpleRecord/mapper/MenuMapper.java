package cn.jackbin.SimpleRecord.mapper;

import cn.jackbin.SimpleRecord.entity.MenuDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.mapper
 * @date: 2020/11/23 21:30
 **/
@Repository
public interface MenuMapper extends BaseMapper<MenuDO> {
    List<MenuDO> queryUserMenus(Long userId);

    List<MenuDO> queryAllMenus();

    List<MenuDO> queryRoleMenus(Long roleId);

    void notDelete(Long id);
}
