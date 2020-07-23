package cn.jackbin.jianzhang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.jianzhang.dto
 * @date: 2020/7/23 20:58
 **/
@Data
@NoArgsConstructor
public class LoginDTO {

    private String username;

    private String password;
}
