package cn.jackbin.SimpleRecord.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 菜单视图类
 * @date: 2020/12/1 19:54
 **/
@Data
@Builder
public class MenuVO {

    private Integer id;

    /**
     * 父节点Id
     */
    private Integer parentId;

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
    private Boolean isOuterChain;

    private String menuType;

    private String iconName;

    private String permissionSign;

    List<MenuVO> children;

    public MenuVO() {
    }

    public MenuVO(Integer id, Integer parentId, String menuName, String path, String component, Boolean isOuterChain, String menuType, String iconName, String permissionSign, List<MenuVO> children) {
        this.id = id;
        this.parentId = parentId;
        this.menuName = menuName;
        this.path = path;
        this.component = component;
        this.isOuterChain = isOuterChain;
        this.menuType = menuType;
        this.iconName = iconName;
        this.permissionSign = permissionSign;
        this.children = children;
    }
}
