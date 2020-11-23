package cn.jackbin.SimpleRecord.service;

import cn.jackbin.SimpleRecord.entity.MenuDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.service
 * @date: 2020/11/23 21:35
 **/
public interface MenuService extends IService<MenuDO> {

    /**
     * 获取指定用户的权限
     */
    List<MenuDO> getUserMenus(Long userId);

    /**
     * 通过id查找指定的权限
     */
    MenuDO getById(Long id);

    /**
     * 获取所有的权限
     */
    List<MenuDO> getAll();

    /**
     * 分页获取权限
     */
    List<MenuDO> getByPage(int pageIndex, int pageSize);
}
