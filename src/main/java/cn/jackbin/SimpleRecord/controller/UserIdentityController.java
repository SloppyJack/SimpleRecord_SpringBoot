package cn.jackbin.SimpleRecord.controller;


import cn.jackbin.SimpleRecord.dto.Result;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/jianzhang/tb-user-identity")
@Slf4j
public class UserIdentityController {

    @GetMapping
    public Result get() {
        log.info("打印测试信息");
        return Result.success();
    }

}
