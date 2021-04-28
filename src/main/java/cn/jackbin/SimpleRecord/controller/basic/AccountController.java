package cn.jackbin.SimpleRecord.controller.basic;

import cn.jackbin.SimpleRecord.common.LocalUser;
import cn.jackbin.SimpleRecord.common.anotations.LoginRequired;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.service.UserService;
import cn.jackbin.SimpleRecord.vo.AccountBaseSettingVO;
import cn.jackbin.SimpleRecord.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 账户相关
 * @date: 2021/3/8 20:25
 **/
@Api(value = "AccountController", tags = { "账户设置接口" })
@RestController
@RequestMapping(value = "/account")
public class AccountController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "账户基本设置")
    @LoginRequired
    @PutMapping(value = "/baseSetting")
    public Result<?> saveInfo(@RequestBody @Validated AccountBaseSettingVO vo) {
        UserDO user = LocalUser.get();
        user.setNickname(vo.getNickname());
        user.setSex(vo.getSex());
        userService.updateById(user);
        return Result.success();
    }

}
