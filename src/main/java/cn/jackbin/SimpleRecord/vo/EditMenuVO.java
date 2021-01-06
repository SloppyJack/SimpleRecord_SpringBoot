package cn.jackbin.SimpleRecord.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.vo
 * @date: 2020/12/29 20:02
 **/
@Data
@NoArgsConstructor
public class EditMenuVO {

    @Positive(message = "id为正数")
    private Integer id;

    @NotNull(message = "菜单标题不能为空")
    private String menuTitle;

    @NotNull(message = "菜单名称不能为空")
    private String menuName;

    private Integer parentId;

    private Integer orderNo;

    private String path;

    private String component;

    private Boolean isOuterChain;

    private String menuType;

    private String permissionSign;

    private String iconName;
}
