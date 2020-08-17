package cn.jackbin.SimpleRecord.vo;

import cn.jackbin.SimpleRecord.entity.GroupDO;
import cn.jackbin.SimpleRecord.entity.PermissionDO;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.entity.UserIdentityDO;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: user的视图类
 * @date: 2020/8/3 20:33
 **/
@Data
@AllArgsConstructor
public class UserGroupVO {

    /**
     * 用户类
     */
    private UserDO userDO;

    /**
     * 用户特征类
     */
    private UserIdentityDO userIdentityDO;

    /**
     * 权限实体类
     */
    private GroupDO groupDO;

}
