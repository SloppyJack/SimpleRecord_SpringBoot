package cn.jackbin.SimpleRecord.controller.basic;


import cn.jackbin.SimpleRecord.bo.MenuBO;
import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.entity.MenuDO;
import cn.jackbin.SimpleRecord.service.MenuService;
import cn.jackbin.SimpleRecord.vo.AddMenuVO;
import cn.jackbin.SimpleRecord.vo.GetMenusVO;
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

    @ApiOperation(value = "分页获取权限列表")
    @PostMapping(value = "/page")
    public Result<?> getByPage(@RequestBody @Validated GetMenusVO vo) {
        // 权限列表
        PageBO<MenuBO> pageBO = menuService.getByPage(vo.getTitle(), vo.getDeleted(), vo.getDate(), vo.getPageNo() - 1, vo.getPageSize());
        return Result.success(pageBO);
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
        // todo 待完成
        vo.getIconName();
        return Result.success();
    }
}
