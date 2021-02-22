package cn.jackbin.SimpleRecord.controller.basic;


import cn.jackbin.SimpleRecord.bo.MenuBO;
import cn.jackbin.SimpleRecord.common.LocalUserId;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.entity.MenuDO;
import cn.jackbin.SimpleRecord.entity.RoleDO;
import cn.jackbin.SimpleRecord.service.MenuService;
import cn.jackbin.SimpleRecord.service.RoleService;
import cn.jackbin.SimpleRecord.vo.*;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.*;

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
        List<MenuBO> menuBOS =  menuService.copyFromMenuDos(menuDOS);
        List<MenuBO> tree = menuService.generatorMenuTree(menuBOS);
        RoleMenuVO roleMenuVO = new RoleMenuVO(roleDOS, tree);
        return Result.success(roleMenuVO);
    }

    @ApiOperation(value = "获取角色列表")
    @PostMapping("/page")
    public Result<?> getRoleList(@RequestBody @Validated GetRolesVO vo) {
        return Result.success(roleService.getByPage(vo.getName(), vo.getDeleted(), vo.getDate(), vo.getPageNo() - 1, vo.getPageSize()));
    }

    @ApiModelProperty
    @PostMapping("/add")
    public Result<?> addRole(@RequestBody @Validated AddRoleVO vo) {
        if (roleService.add(vo.getName(), vo.getInfo())) {
            return Result.success();
        } else {
            return Result.error(CodeMsg.ADD_DATA_ERROR);
        }
    }

    @ApiOperation(value = "角色所拥有的权限")
    @GetMapping(value = "/ownedMenus/{roleId}")
    public Result<?> getOwnedMenus(@ApiParam("角色Id") @Validated
                                   @Positive(message = "角色Id为正数") @PathVariable("roleId") Integer roleId) {
        // 所有权限
        List<MenuDO> allMenus = menuService.getAll();
        // 角色的权限
        List<MenuDO> roleMenus = menuService.getRoleMenus((long)roleId);
        List<MenuBO> boList = menuService.copyFromMenuDos(allMenus);
        // 此处使用字典
        Map<Integer,Object> dictionaries = new HashMap<>();
        roleMenus.forEach(n -> dictionaries.put(n.getId().intValue(), null));
        boList.forEach(n -> {
            if (dictionaries.containsKey(n.getId())) {
                n.setOwned(true);
            }
        });
        List<MenuBO> finalMenus = menuService.generatorMenuTree(boList);
        return Result.success(finalMenus);
    }

    @ApiOperation(value = "编辑角色")
    @PutMapping(value = "/edit")
    public Result<?> editRole(@RequestBody @Validated EditRoleVO vo) {
        roleService.editRole(vo.getId(), vo.getName(), vo.getInfo(), vo.getMenuIds());
        return Result.success();
    }
}
