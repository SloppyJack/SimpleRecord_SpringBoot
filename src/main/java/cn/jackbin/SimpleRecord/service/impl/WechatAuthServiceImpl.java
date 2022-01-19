package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.common.config.sercurity.JWTConfig;
import cn.jackbin.SimpleRecord.common.manager.AsyncManager;
import cn.jackbin.SimpleRecord.constant.RedisKey;
import cn.jackbin.SimpleRecord.constant.UrlConstant;
import cn.jackbin.SimpleRecord.entity.MenuDO;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.entity.WechatUserDO;
import cn.jackbin.SimpleRecord.mapper.WechatUserMapper;
import cn.jackbin.SimpleRecord.service.*;
import cn.jackbin.SimpleRecord.utils.HttpUtil;
import cn.jackbin.SimpleRecord.utils.PasswordUtil;
import cn.jackbin.SimpleRecord.utils.RedisUtil;
import cn.jackbin.SimpleRecord.utils.SpringContextUtil;
import cn.jackbin.SimpleRecord.vo.LoginSuccessVO;
import cn.jackbin.SimpleRecord.vo.WechatUserVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.service.impl
 * @date: 2020/11/3 21:34
 **/
@Slf4j
@Service
public class WechatAuthServiceImpl extends ServiceImpl<WechatUserMapper, WechatUserDO> implements WechatAuthService {
    @Value("${wechat.appId}")
    private String appId;
    @Value("${wechat.appSecret}")
    private String appSecret;

    @Autowired
    private WechatUserMapper wechatUserMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RecordUserCategoryService recordUserCategoryService;
    @Autowired
    private RecordBookService recordBookService;
    @Autowired
    private RecordAccountService recordAccountService;

    @Override
    public WechatUserDO getByOpenId(String openId) {
        QueryWrapper<WechatUserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("open_id",openId);
        return wechatUserMapper.selectOne(queryWrapper);
    }

    @Transactional
    @Override
    public LoginSuccessVO wechatLogin(WechatUserVO vo) {
        UserDO userDO;
        WechatUserDO wechatUserDO = getByOpenId(vo.getOpenId());
        if (wechatUserDO == null) {
            // 1. 先注册
            UserDO userTemp = new UserDO();
            BeanUtils.copyProperties(vo, userTemp);
            userTemp.setCredential(PasswordUtil.encoder(PasswordUtil.randomPsw()));
            // 2. 保存用户
            userService.saveWithDefaultRole(userTemp);
            // 3. 从系统中初始化类别
            recordUserCategoryService.reset(userTemp.getId().intValue());
            // 4. 初始化账单
            recordBookService.init(userTemp.getId().intValue());
            // 5. 初始化记账账户
            recordAccountService.init(userTemp.getId().intValue());
            userDO = userService.getByName(vo.getUsername());
            WechatUserDO wechatUserTemp = new WechatUserDO();
            wechatUserTemp.setUserId(userDO.getId());
            wechatUserTemp.setOpenId(vo.getOpenId());
            // 保存到微信用户表
            save(wechatUserTemp);
        }else {
            userDO = userService.getById(wechatUserDO.getUserId());
        }
        LoginSuccessVO loginSuccessVO = new LoginSuccessVO();
        BeanUtils.copyProperties(userDO, loginSuccessVO);
        loginSuccessVO.setToken(generateToken(userDO.getId()));
        // 添加openId作为返回
        loginSuccessVO.setOpenId(vo.getOpenId());
        return loginSuccessVO;
    }

    @Override
    public String getAccessToken() {
        if (redisUtil.hasKey(RedisKey.WECHAT_ACCESS_TOKEN)) {
            return (String) redisUtil.get(RedisKey.WECHAT_ACCESS_TOKEN);
        }
        // 如果缓存中没有，则请求出来在放入redis
        Map<String, String> map = new HashMap<>();
        map.put("grant_type", "client_credential");
        map.put("appid", appId);
        map.put("secret", appSecret);
        String result = HttpUtil.doGet(UrlConstant.wechatAccessTokenUrl, map);
        JSONObject obj = JSON.parseObject(result);
        // 解析到token和过期时间
        AsyncManager.me().execute(
                () -> redisUtil.set(RedisKey.WECHAT_ACCESS_TOKEN, obj.get("access_token"), Integer.parseInt(obj.get("expires_in").toString()) - 60)
        );
        return obj.get("access_token").toString();
    }

    @Override
    public byte[] getMiniAppQrcode(String accessToken, String scene, String pagePath) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("scene", scene);
        map.put("page", pagePath);
        return HttpUtil.post(UrlConstant.wechatMiniAppQrcodeUnlimitedUrl + accessToken, JSON.toJSONString(map));
    }

    private String generateToken(Long userId) {
        List<MenuDO> menuDOList = menuService.getUserMenus(userId);
        List<String> permissionList = new ArrayList<>();
        // 跳过空权限字符（注：菜单和目录没有权限字符）
        menuDOList.stream().filter(n -> StringUtils.isNoneBlank(n.getPermissionSign())).forEach(n -> permissionList.add(n.getPermissionSign()));
        JWTConfig jwtConfig = SpringContextUtil.getBean(JWTConfig.class);
        return jwtConfig.createToken(userId.toString(),permissionList);
    }
}
