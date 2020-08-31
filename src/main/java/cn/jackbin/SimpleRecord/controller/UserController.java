package cn.jackbin.SimpleRecord.controller;


import cn.jackbin.SimpleRecord.common.config.JWTConfig;
import cn.jackbin.SimpleRecord.dto.LoginDTO;
import cn.jackbin.SimpleRecord.dto.Result;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.service.UserGroupService;
import cn.jackbin.SimpleRecord.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private JWTConfig jwtConfig;
    @Autowired
    private UserGroupService userGroupService;


    @GetMapping
    @ResponseBody
    public Result getUserById() {
        log.error("its the test error info");
        UserDO userDO = userService.getBaseMapper().selectById(1);
        return Result.success(userDO);
    }
}
