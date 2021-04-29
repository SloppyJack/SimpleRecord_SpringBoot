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

    // 是否已扫描
    private Boolean isScanned = false;

    // 是否过期
    private Boolean isExpired = false;

    // 扫码登录时间
    private String scannedTime;

    // token字符
    private String token;
}
