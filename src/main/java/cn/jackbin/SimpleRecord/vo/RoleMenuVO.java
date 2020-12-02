package cn.jackbin.SimpleRecord.vo;

import cn.jackbin.SimpleRecord.entity.RoleDO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.vo
 * @date: 2020/11/24 20:41
 **/
@Data
@AllArgsConstructor
public class RoleMenuVO {
    private List<RoleDO> roles;

    private List<MenuVO> menus;
}
