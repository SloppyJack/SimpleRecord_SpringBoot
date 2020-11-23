package cn.jackbin.SimpleRecord.bo;

import cn.jackbin.SimpleRecord.entity.MenuDO;
import cn.jackbin.SimpleRecord.entity.UserDO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: user的视图类
 * @date: 2020/8/3 20:33
 **/
@Data
@AllArgsConstructor
public class UserMenuBO {

    /**
     * 用户类
     */
    private UserDO userDO;

    /**
     * 权限列表
     */
    private List<MenuDO> menuDOList;

}
