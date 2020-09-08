package cn.jackbin.SimpleRecord.controller;


import cn.jackbin.SimpleRecord.dto.PageDTO;
import cn.jackbin.SimpleRecord.dto.Result;
import cn.jackbin.SimpleRecord.entity.PermissionDO;
import cn.jackbin.SimpleRecord.service.PermissionService;
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
@RestController
@RequestMapping("/permission")
public class TbPermissionController {
    @Autowired
    private PermissionService permissionService;
    @GetMapping
    public Result get(@RequestParam(value = "id")Integer id) {
        if (id == null) {
            List<PermissionDO> list = permissionService.getAll();
            return Result.success(list);
        } else {
            PermissionDO permissionDO = permissionService.getById(id);
            return Result.success(permissionDO);
        }
    }

    @GetMapping(value = "/getByPage")
    public Result getByPage(@Validated PageDTO dto) {
        List<PermissionDO> list = permissionService.getByPage(dto.getPageIndex(), dto.getPageSize());
        return Result.success(list);
    }
}
