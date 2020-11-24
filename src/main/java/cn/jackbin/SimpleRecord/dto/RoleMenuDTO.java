package cn.jackbin.SimpleRecord.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.dto
 * @date: 2020/11/24 20:18
 **/
@Data
@NoArgsConstructor
public class RoleMenuDTO {

    /**
     * 角色Id
     */
    private Long id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 角色信息
     */
    private String info;

    private Menu menu;
}
