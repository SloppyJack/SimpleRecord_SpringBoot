package cn.jackbin.SimpleRecord.controller.basic;

import cn.jackbin.SimpleRecord.vo.LoginVO;
import cn.jackbin.SimpleRecord.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 此类仅用于生成Swagger接口信息，并无实际用处
 * @date: 2021/2/1 20:48
 **/
@Api(value = "LoginController", tags = { "用户登录接口" })
@RequestMapping(value = "/")
public class LoginController {

    @ApiOperation(value = "用户登录")
    @PostMapping("user/login")
    public Result<?> userLogin(@RequestBody LoginVO dto) {
        return Result.success();
    }
}
