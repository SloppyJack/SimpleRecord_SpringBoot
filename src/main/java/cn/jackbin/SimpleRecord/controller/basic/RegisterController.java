package cn.jackbin.SimpleRecord.controller.basic;

import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.constant.SexConstant;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.service.UserService;
import cn.jackbin.SimpleRecord.utils.PasswordUtil;
import cn.jackbin.SimpleRecord.vo.RegisterVO;
import cn.jackbin.SimpleRecord.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.controller.basic
 * @date: 2021/2/1 20:48
 **/
@RequestMapping(value = "/")
public class RegisterController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "注册用户")
    @PostMapping(value = "user/register")
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
        userService.saveWithDefaultRole(userDO);
        return Result.success();
    }
}
