package cn.jackbin.SimpleRecord.controller;


import cn.jackbin.SimpleRecord.common.config.JWTConfig;
import cn.jackbin.SimpleRecord.common.ioc.LoginRequired;
import cn.jackbin.SimpleRecord.dto.LoginDTO;
import cn.jackbin.SimpleRecord.dto.Result;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.exception.NotFoundException;
import cn.jackbin.SimpleRecord.exception.ParameterException;
import cn.jackbin.SimpleRecord.service.UserIdentityService;
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
    private UserIdentityService userIdentityService;
    @Autowired
    private JWTConfig jwtConfig;


    @GetMapping
    @ResponseBody
    @LoginRequired
    public Result getUserById() {
        log.error("its the test error info");
        UserDO userDO = userService.getBaseMapper().selectById(1);
        return Result.success(userDO);
    }

    /**
     * 用户登陆
     */
    @PostMapping("/login")
    public Result login(@RequestBody @Validated LoginDTO validator) {
        UserDO user = userService.getUserByUserName(validator.getUsername());
        if (user == null) {
            throw new NotFoundException("未找到指定用户");
        }
        boolean valid = userIdentityService.verifyUsernamePassword(
                user.getId(),
                user.getUsername(),
                validator.getPassword());
        if (!valid) {
            throw new ParameterException("用户名或密码错误");
        }
        return Result.success(jwtConfig.createToken(user.getId().toString()));
    }
}
