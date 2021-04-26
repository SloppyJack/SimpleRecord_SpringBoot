package cn.jackbin.SimpleRecord.bo;

import lombok.Data;


/**
 * @author: create by bin
 * @version: v1.0
 * @description: 二维码信息
 * @date: 2021/4/19 22:23
 **/
@Data
public class QrCodeInfoBO {
    // 是否登录
    private Boolean isLogin = false;

    // 是否已扫描
    private Boolean isScanned = false;

    // 扫码登录时间
    private String loginTime;

    // 用户的IP地址
    private String ipAddr;

    // token字符
    private String token;
}
