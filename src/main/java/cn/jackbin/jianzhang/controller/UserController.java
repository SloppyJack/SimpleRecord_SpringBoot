package cn.jackbin.jianzhang.controller;


import cn.jackbin.jianzhang.entity.UserDO;
import cn.jackbin.jianzhang.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public UserDO getUserById() {
        log.error("its the test error info");
        UserDO userDO = userService.getBaseMapper().selectById(1);
        return userDO;
    }
}
