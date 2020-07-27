package cn.jackbin.jianzhang.controller;


import cn.jackbin.jianzhang.common.config.JWTConfig;
import cn.jackbin.jianzhang.dto.LoginDTO;
import cn.jackbin.jianzhang.dto.Result;
import cn.jackbin.jianzhang.entity.UserDO;
import cn.jackbin.jianzhang.exception.NotFoundException;
import cn.jackbin.jianzhang.service.UserService;
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


    @GetMapping
    public UserDO getUserById() {
        log.error("its the test error info");
        UserDO userDO = userService.getBaseMapper().selectById(1);
        return userDO;
    }

    /**
     * 用户登陆
     */
    @PostMapping("/login")
    public Result login(@RequestBody @Validated LoginDTO validator) {
        UserDO user = userService.getUserByUserName(validator.getUsername());
        if (user == null) {
            throw new NotFoundException("未找到用户");
        }
        /*boolean valid = userIdentityService.verifyUsernamePassword(
                user.getId(),
                user.getUsername(),
                validator.getPassword());
        if (!valid) {
            throw new ParameterException("username or password is fault", 10031);
        }*/
        return Result.success(jwtConfig.createToken("123"));
    }
}
