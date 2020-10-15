package cn.jackbin.SimpleRecord.controller.basic;


import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.vo.PageVO;
import cn.jackbin.SimpleRecord.vo.Result;
import cn.jackbin.SimpleRecord.entity.PermissionDO;
import cn.jackbin.SimpleRecord.service.PermissionService;
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
@Api(value = "PermissionController", tags = { "权限访问接口" })
@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "获取指定权限")
    @GetMapping
    public Result<?> get(@ApiParam(value = "权限Id") @Validated
                          @Positive(message = "权限Id为整数") @RequestParam(value = "id")Integer id) {
        PermissionDO permissionDO = permissionService.getById(id);
        if (permissionDO == null) {
            return Result.error(CodeMsg.NOT_FIND_DATA);
        }
        return Result.success(permissionDO);
    }

    @ApiOperation(value = "分页获取权限列表")
    @GetMapping(value = "/getByPage")
    public Result<?> getByPage(@Validated PageVO vo) {
        List<PermissionDO> list = permissionService.getByPage(vo.getPageIndex(), vo.getPageSize());
        return Result.success(list);
    }
}
