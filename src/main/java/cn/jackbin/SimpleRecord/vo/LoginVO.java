package cn.jackbin.SimpleRecord.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.dto
 * @date: 2020/7/23 20:58
 **/
@ApiModel(value="LoginDTO对象", description="用户登录对象")
@Data
public class LoginVO {

    @ApiModelProperty(required = true, value = "用户名")
    private String username;

    @ApiModelProperty(required = true, value = "密码")
    private String password;
}
