package cn.jackbin.SimpleRecord.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.vo
 * @date: 2021/4/21 20:21
 **/
@Data
public class QrcodeAuthorizationVO {
    @NotNull(message = "uuid不能为空")
    private String uuid;

    @NotNull(message = "token不能为空")
    private String token;
}
