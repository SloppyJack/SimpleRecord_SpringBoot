package cn.jackbin.SimpleRecord.controller;


import cn.jackbin.SimpleRecord.dto.PageDTO;
import cn.jackbin.SimpleRecord.dto.Result;
import cn.jackbin.SimpleRecord.entity.PermissionDO;
import cn.jackbin.SimpleRecord.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "获取权限列表")
    @GetMapping
    public Result get(@ApiParam(required = true, value = "带id则获取指定权限") @RequestParam(value = "id")Integer id) {
        if (id == null) {
            List<PermissionDO> list = permissionService.getAll();
            return Result.success(list);
        } else {
            PermissionDO permissionDO = permissionService.getById(id);
            return Result.success(permissionDO);
        }
    }

    @ApiOperation(value = "分页获取权限列表")
    @GetMapping(value = "/getByPage")
    public Result getByPage(@Validated PageDTO dto) {
        List<PermissionDO> list = permissionService.getByPage(dto.getPageIndex(), dto.getPageSize());
        return Result.success(list);
    }
}
