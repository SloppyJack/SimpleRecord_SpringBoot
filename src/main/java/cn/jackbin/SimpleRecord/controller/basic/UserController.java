package cn.jackbin.SimpleRecord.controller.basic;


import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.constant.SexConstant;
import cn.jackbin.SimpleRecord.vo.*;
import cn.jackbin.SimpleRecord.vo.PageVO;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.service.UserService;
import cn.jackbin.SimpleRecord.utils.PasswordUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
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
    public Result<?> register(@RequestBody @Validated RegisterVO vo) {
        if (userService.getByName(vo.getUsername()) != null) {
            return Result.error(CodeMsg.USERNAME_EXIST);
        }
        if (!PasswordUtil.check(vo.getPassword())) {
            return Result.error(CodeMsg.PSW_FORMAT_ERROR);
        }
        if (SexConstant.MAN !=vo.getSex() && SexConstant.WOMAN != vo.getSex()) {
            return Result.error(CodeMsg.SEX_FORMAT_ERROR);
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(vo, userDO);
        userDO.setCredential(PasswordUtil.encoder(vo.getPassword()));
        userService.save(userDO);
        return Result.success();
    }

    @ApiOperation(value = "用户登录")
    @PostMapping(value = "/login")
    public Result<?> login(@RequestBody LoginVO dto) {
        return Result.success();
    }

    @ApiOperation(value = "通过Id获取用户")
    @GetMapping
    public Result<?> get(@ApiParam(required = true, value = "用户Id") @Validated
                          @Positive(message = "用户Id为整数") @RequestParam(value = "id")Integer id) {
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
    public Result<?> get(@ApiParam(required = true, value = "用户名") @Validated
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
    public Result<?> getByPage(@Validated PageVO vo) {
        List<UserDO> userDOList = userService.getByPage(vo.getPageIndex(), vo.getPageSize());
        List<UserVO> userVOList = new ArrayList<>();
        BeanUtils.copyProperties(userDOList, userVOList);
        return Result.success(userVOList);
    }
}
