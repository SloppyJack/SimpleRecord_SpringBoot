package cn.jackbin.SimpleRecord.dto;

import cn.jackbin.SimpleRecord.entity.RoleDO;
import cn.jackbin.SimpleRecord.entity.UserRoleDO;
import lombok.Data;

import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.dto
 * @date: 2021/2/4 20:33
 **/
@Data
public class UserRoleDTO {
    private Long id;

    /**
     * 用户名，唯一
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 头像url
     */
    private String avatarUrl;

    /**
     * 邮箱
     */
    private String email;

    List<RoleDO> ownedRoles;

    List<RoleDO> allRoles;
}
