package cn.jackbin.SimpleRecord.vo;

import cn.jackbin.SimpleRecord.entity.GroupDO;
import cn.jackbin.SimpleRecord.entity.PermissionDO;
import cn.jackbin.SimpleRecord.entity.UserDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 用户组权限视图类
 * @date: 2020/8/4 21:16
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPermissionVO {
    /**
     * 用户实体类
     */
    private UserDO userDO;

    /**
     * 权限实体类
     */
    private PermissionDO permissionDO;
}
