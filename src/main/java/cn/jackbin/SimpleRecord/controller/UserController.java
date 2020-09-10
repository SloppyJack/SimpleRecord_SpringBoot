package cn.jackbin.SimpleRecord.controller;


import cn.jackbin.SimpleRecord.constant.SexConstant;
import cn.jackbin.SimpleRecord.dto.*;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.service.UserService;
import cn.jackbin.SimpleRecord.util.PasswordUtil;
import cn.jackbin.SimpleRecord.vo.UserVO;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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

    @ApiOperation(value = "注册用户")
    @PostMapping(value = "/register")
    public Result register(@RequestBody @Validated RegisterDTO dto) {
        if (userService.getByName(dto.getUsername()) != null) {
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

    @ApiOperation(value = "通过Id获取用户")
    @GetMapping
    public Result get(@ApiParam(required = true, value = "用户Id") @Validated
                          @NotNull(message = "用户Id不能为空") @RequestParam(value = "id")Integer id) {
        UserDO userDO = userService.getById(id);
        if (userDO == null) {
            return Result.error(CodeMsg.NOT_FIND_DATA);
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDO, userVO);
        return Result.success(userVO);
    }

    @ApiOperation(value = "通过Name获取用户")
    @GetMapping(value = "/getByName")
    public Result get(@ApiParam(required = true, value = "用户名") @Validated
                          @NotBlank(message = "用户名不能为空") @RequestParam(value = "id")String userName) {
        UserDO userDO = userService.getByName(userName);
        if (userDO == null) {
            return Result.error(CodeMsg.NOT_FIND_DATA);
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDO, userVO);
        return Result.success(userVO);
    }

    @ApiOperation(value = "分页获取用户列表")
    @GetMapping(value = "/getByPage")
    public Result getByPage(@Validated PageDTO dto) {
        List<UserDO> userDOList = userService.getByPage(dto.getPageIndex(), dto.getPageSize());
        List<UserVO> userVOList = new ArrayList<>();
        BeanUtils.copyProperties(userDOList, userVOList);
        return Result.success(userVOList);
    }
}
