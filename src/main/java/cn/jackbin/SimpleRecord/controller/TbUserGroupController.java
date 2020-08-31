package cn.jackbin.SimpleRecord.controller;


import cn.jackbin.SimpleRecord.dto.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jackbin
 * @since 2020-07-21
 */
@RestController
@RequestMapping("/jianzhang/tb-user-group")
public class TbUserGroupController {

    @PreAuthorize("hasAuthority('log:find1')")
    @GetMapping()
    public Result test() {
        System.out.println("test");
        return Result.success();
    }
}
