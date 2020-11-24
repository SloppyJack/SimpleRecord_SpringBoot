package cn.jackbin.SimpleRecord.controller.basic;


import cn.jackbin.SimpleRecord.common.LocalUserId;
import cn.jackbin.SimpleRecord.entity.MenuDO;
import cn.jackbin.SimpleRecord.entity.RoleDO;
import cn.jackbin.SimpleRecord.service.MenuService;
import cn.jackbin.SimpleRecord.service.RoleService;
import cn.jackbin.SimpleRecord.vo.Result;
import cn.jackbin.SimpleRecord.vo.RoleMenuVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jackbin
 * @since 2020-07-21
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "获取用户的角色及菜单")
    @GetMapping("/menus")
    public Result<?> getRoleMenus() {
        // 用户角色
        List<RoleDO> roleDOS = roleService.getByUserId(LocalUserId.get());
        // 用户菜单权限
        List<MenuDO> menuDOS = menuService.getUserMenus(LocalUserId.get());
        RoleMenuVO roleMenuVO = new RoleMenuVO(roleDOS, menuDOS);
        return Result.success(roleMenuVO);
    }

}
