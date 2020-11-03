package cn.jackbin.SimpleRecord.controller.auth;

import cn.hutool.http.HttpUtil;
import cn.jackbin.SimpleRecord.bo.UserPermissionBO;
import cn.jackbin.SimpleRecord.common.config.JWTConfig;
import cn.jackbin.SimpleRecord.common.config.sercurity.JWTUser;
import cn.jackbin.SimpleRecord.constant.UrlConstant;
import cn.jackbin.SimpleRecord.entity.PermissionDO;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.entity.WechatUserDO;
import cn.jackbin.SimpleRecord.service.PermissionService;
import cn.jackbin.SimpleRecord.service.UserService;
import cn.jackbin.SimpleRecord.service.WechatUserService;
import cn.jackbin.SimpleRecord.utils.PasswordUtil;
import cn.jackbin.SimpleRecord.utils.SpringContextUtil;
import cn.jackbin.SimpleRecord.vo.LoginSuccessVO;
import cn.jackbin.SimpleRecord.vo.Result;
import cn.jackbin.SimpleRecord.vo.WechatUserVO;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.controller.auth
 * @date: 2020/11/2 20:45
 **/
@Api(value = "WechatAuthController", tags = { "微信授权接口" })
@RestController
@RequestMapping("/wx")
public class WechatAuthController {
    @Value("${wechat.appId}")
    private String appId;
    @Value("${wechat.appSecret}")
    private String appSecret;

    @Autowired
    private WechatUserService wechatUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "获取微信openId")
    @GetMapping("/openId/{code}")
    public Result<?> getOpenId(@Validated @NotNull(message = "code不能为空") @PathVariable("code") String code) {
        Map<String, Object> map = new HashMap<>();
        map.put("appid", appId);
        map.put("secret", appSecret);
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String result = HttpUtil.get(UrlConstant.wechatOpenIdUrl, map);
        return Result.success(JSON.parse(result));
    }

    @ApiOperation(value = "微信登录授权")
    @PostMapping("/login")
    public Result<?> login(@RequestBody WechatUserVO vo) {
        UserDO userDO;
        WechatUserDO wechatUserDO = wechatUserService.getByOpenId(vo.getOpenId());
        if (wechatUserDO == null) {
            // 先注册
            UserDO userTemp = new UserDO();
            BeanUtils.copyProperties(vo, userTemp);
            userTemp.setCredential(PasswordUtil.encoder(PasswordUtil.randomPsw()));
            userService.save(userTemp);
            userDO = userService.getByName(vo.getUsername());
            WechatUserDO wechatUserTemp = new WechatUserDO();
            wechatUserTemp.setUserId(userDO.getId());
            wechatUserTemp.setOpenId(vo.getOpenId());
            wechatUserService.save(wechatUserTemp);
        }else {
            userDO = userService.getById(wechatUserDO.getUserId());
        }
        LoginSuccessVO loginSuccessVO = new LoginSuccessVO();
        BeanUtils.copyProperties(userDO, loginSuccessVO);
        loginSuccessVO.setToken(generateToken(userDO.getId()));
        return Result.success(loginSuccessVO);
    }

    private String generateToken(Long userId) {
        List<PermissionDO> permissionDOList = permissionService.getUserPermissions(userId);
        List<String> permissionList = new ArrayList<>();
        permissionDOList.forEach(n -> permissionList.add(n.getModuleCode()+JWTUser.Spacer+n.getPermissionCode()));
        JWTConfig jwtConfig = SpringContextUtil.getBean(JWTConfig.class);
        return jwtConfig.createToken(userId.toString(),permissionList);
    }
}
