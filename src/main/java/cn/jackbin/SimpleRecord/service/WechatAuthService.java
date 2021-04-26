package cn.jackbin.SimpleRecord.service;

import cn.jackbin.SimpleRecord.entity.WechatUserDO;
import cn.jackbin.SimpleRecord.vo.LoginSuccessVO;
import cn.jackbin.SimpleRecord.vo.WechatUserVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.service
 * @date: 2020/11/3 21:33
 **/
public interface WechatAuthService extends IService<WechatUserDO> {

    WechatUserDO getByOpenId(String openId);

    LoginSuccessVO wechatLogin(WechatUserVO vo);

    /**
     * 获取接口凭证
     */
    String getAccessToken();

    /**
     * 获取小程序码
     */
    byte[] getMiniAppQrcode(String accessToken, String scene, String pagePath) throws IOException;
}
