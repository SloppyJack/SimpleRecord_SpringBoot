package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.common.config.JWTConfig;
import cn.jackbin.SimpleRecord.entity.MenuDO;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.entity.UserRoleDO;
import cn.jackbin.SimpleRecord.entity.WechatUserDO;
import cn.jackbin.SimpleRecord.mapper.WechatUserMapper;
import cn.jackbin.SimpleRecord.service.*;
import cn.jackbin.SimpleRecord.utils.PasswordUtil;
import cn.jackbin.SimpleRecord.utils.SpringContextUtil;
import cn.jackbin.SimpleRecord.vo.LoginSuccessVO;
import cn.jackbin.SimpleRecord.vo.WechatUserVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.service.impl
 * @date: 2020/11/3 21:34
 **/
@Service
public class WechatUserServiceImpl extends ServiceImpl<WechatUserMapper, WechatUserDO> implements WechatUserService {
    @Autowired
    private WechatUserMapper wechatUserMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

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
            // 先注册
            UserDO userTemp = new UserDO();
            BeanUtils.copyProperties(vo, userTemp);
            userTemp.setCredential(PasswordUtil.encoder(PasswordUtil.randomPsw()));
            // 保存用户
            userService.saveWithDefaultRole(userTemp);
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

    private String generateToken(Long userId) {
        List<MenuDO> menuDOList = menuService.getUserMenus(userId);
        List<String> permissionList = new ArrayList<>();
        menuDOList.forEach(n -> permissionList.add(n.getPermissionSign()));
        JWTConfig jwtConfig = SpringContextUtil.getBean(JWTConfig.class);
        return jwtConfig.createToken(userId.toString(),permissionList);
    }
}
