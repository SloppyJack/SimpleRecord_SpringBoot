package cn.jackbin.SimpleRecord.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.dto
 * @date: 2020/8/31 19:38
 **/
@ApiModel(value="RegisterDTO对象", description="用户注册对象")
@Data
public class RegisterVO {

    @ApiModelProperty(required = true, value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(required = true, value = "用户昵称")
    @NotBlank(message = "用户昵称不能为空")
    private String nickname;

    @ApiModelProperty(required = true, value = "性别（男:1;女:2）")
    @NotNull(message = "性别不能为空")
    private Integer sex;

    @ApiModelProperty(required = true, value = "邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;

    @ApiModelProperty(required = true, value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
