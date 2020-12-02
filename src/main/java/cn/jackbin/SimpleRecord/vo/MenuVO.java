package cn.jackbin.SimpleRecord.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 菜单视图类
 * @date: 2020/12/1 19:54
 **/
@Data
@AllArgsConstructor
@Builder
public class MenuVO {

    private Integer id;

    /**
     * 父节点Id
     */
    private Integer parentId;

    /**
     * 菜单标题
     */
    private String menuTitle;

    /**
     * 菜单名
     */
    private String menuName;

    /**
     * 路径
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 是否为外链
     */
    private boolean isOuterChain;

    private String menuType;

    private String iconName;

    private String permissionSign;

    private Integer orderNo;

    List<MenuVO> children;

    public MenuVO() {
    }
}
