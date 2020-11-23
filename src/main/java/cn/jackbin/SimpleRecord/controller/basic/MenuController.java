package cn.jackbin.SimpleRecord.controller.basic;


import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.entity.MenuDO;
import cn.jackbin.SimpleRecord.service.MenuService;
import cn.jackbin.SimpleRecord.vo.PageVO;
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
    public Result<?> getByPage(@RequestBody @Validated PageVO vo) {
        List<MenuDO> list = menuService.getByPage(vo.getPageIndex(), vo.getPageSize());
        return Result.success(list);
    }
}
