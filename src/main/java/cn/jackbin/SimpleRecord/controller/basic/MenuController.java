package cn.jackbin.SimpleRecord.controller.basic;


import cn.jackbin.SimpleRecord.bo.MenuBO;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.constant.MenuConstants;
import cn.jackbin.SimpleRecord.entity.MenuDO;
import cn.jackbin.SimpleRecord.service.MenuService;
import cn.jackbin.SimpleRecord.vo.AddMenuVO;
import cn.jackbin.SimpleRecord.vo.EditMenuVO;
import cn.jackbin.SimpleRecord.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jackbin
 * @since 2020-07-21
 */
@Api(value = "PermissionController", tags = { "菜单访问接口" })
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "获取指定权限")
    @GetMapping
    public Result<?> getPermission(@ApiParam(value = "权限Id") @Validated
                          @Positive(message = "权限Id为整数") @RequestParam(value = "id") Integer id) {
        MenuDO menuDO = menuService.getById(id);
        if (menuDO == null) {
            return Result.error(CodeMsg.NOT_FIND_DATA);
        }
        return Result.success(menuDO);
    }

    @ApiOperation(value = "分页获取树形权限列表")
    @GetMapping(value = "/allTree")
    public Result<?> getAllTree() {
        // 权限列表
        List<MenuBO> treeList = menuService.getTreeList();
        return Result.success(treeList);
    }

    @ApiOperation(value = "获取所有的权限")
    @GetMapping(value = "/all")
    public Result<?> getAll() {
        List<MenuDO> menuDOS = menuService.getAll();
        List<MenuBO> menuBOS = menuService.copyFromMenuDos(menuDOS);
        List<MenuBO> tree = menuService.generatorMenuTree(menuBOS);
        return Result.success(tree);
    }

    @ApiOperation(value = "添加菜单")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody @Validated AddMenuVO vo) {
        MenuDO menuDO = toMenuDO(null, vo.getMenuTitle(), vo.getMenuName(), vo.getParentId(), vo.getOrderNo(), vo.getPath(), vo.getComponent(), vo.getIsOuterChain(), vo.getMenuType(), vo.getPermissionSign(), vo.getIconName());
        menuService.save(menuDO);
        return Result.success();
    }

    @ApiOperation(value = "删除菜单")
    @GetMapping(value = "/del/{id}")
    public Result<?> del(@PathVariable @Validated @Positive(message = "菜单Id需为正数") Integer id) {
        menuService.removeById(id);
        return Result.success();
    }

    @ApiOperation(value = "还原菜单")
    @GetMapping(value = "/reset/{id}")
    public Result<?> reset(@PathVariable @Validated @Positive(message = "菜单Id需为正数") Integer id) {
        menuService.reset(id);
        return Result.success();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody @Validated EditMenuVO vo) {
        MenuDO menuDO = toMenuDO(Long.valueOf(vo.getId()), vo.getMenuTitle(), vo.getMenuName(), vo.getParentId(), vo.getOrderNo(), vo.getPath(), vo.getComponent(), vo.getIsOuterChain(), vo.getMenuType(), vo.getPermissionSign(), vo.getIconName());
        boolean flag = menuService.updateById(menuDO);
        if (flag)
            return Result.success();
        else
            return Result.error(CodeMsg.ADD_DATA_ERROR);
    }

    private MenuDO toMenuDO(Long id, String menuTitle, String menuName, Integer parentId, Integer orderNo, String path, String component, Boolean isOuterChain, String menuType, String permissionSign, String iconName) {
        MenuDO menuDO = new MenuDO();
        if (id != null) {
            menuDO.setId(id);
        }
        menuDO.setMenuTitle(menuTitle);
        menuDO.setMenuName(menuName);
        menuDO.setParentId(parentId);
        menuDO.setOrderNo(orderNo);
        menuDO.setPath(path);
        menuDO.setComponent(component);
        if (isOuterChain != null) {
            menuDO.setOuterChain(isOuterChain ? MenuConstants.OC : MenuConstants.notOC);
        }
        menuDO.setMenuType(menuType);
        menuDO.setPermissionSign(permissionSign);
        menuDO.setIconName(iconName);
        return menuDO;
    }
}
