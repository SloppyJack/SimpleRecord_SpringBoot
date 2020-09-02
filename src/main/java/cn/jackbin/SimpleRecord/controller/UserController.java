package cn.jackbin.SimpleRecord.controller;


import cn.jackbin.SimpleRecord.common.config.JWTConfig;
import cn.jackbin.SimpleRecord.constant.SexConstant;
import cn.jackbin.SimpleRecord.dto.CodeMsg;
import cn.jackbin.SimpleRecord.dto.LoginDTO;
import cn.jackbin.SimpleRecord.dto.RegisterDTO;
import cn.jackbin.SimpleRecord.dto.Result;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.service.UserGroupService;
import cn.jackbin.SimpleRecord.service.UserService;
import cn.jackbin.SimpleRecord.util.PasswordUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
@Api(value = "UserController", tags = { "用户访问接口" })
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

    @ApiOperation(value = "注册用户")
    @ApiResponses(value = @ApiResponse(code = 0, message = "成功"))
    @PostMapping(value = "/register")
    public Result register(@RequestBody @Validated RegisterDTO dto) {
        if (userService.getUserByUserName(dto.getUsername()) != null) {
            return Result.error(CodeMsg.USERNAME_EXIST);
        }
        if (!PasswordUtil.check(dto.getPassword())) {
            return Result.error(CodeMsg.PSW_FORMAT_ERROR);
        }
        if (SexConstant.MAN !=dto.getSex() && SexConstant.WOMAN != dto.getSex()) {
            return Result.error(CodeMsg.SEX_FORMAT_ERROR);
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(dto, userDO);
        userDO.setCredential(PasswordUtil.encoder(dto.getPassword()));
        userService.save(userDO);
        return Result.success();
    }

    @ApiOperation(value = "用户登录")
    @PostMapping(value = "/login")
    public Result login(@RequestBody LoginDTO dto) {
        return Result.success();
    }
}
