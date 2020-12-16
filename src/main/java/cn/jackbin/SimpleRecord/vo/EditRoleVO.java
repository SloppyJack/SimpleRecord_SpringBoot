package cn.jackbin.SimpleRecord.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.vo
 * @date: 2020/12/16 20:17
 **/
@ApiModel(value="EditRoleVO对象", description="编辑角色对象")
@Data
@NoArgsConstructor
public class EditRoleVO {

    @Positive(message = "角色Id为正数")
    private Long id;

    @NotNull(message = "角色名不能为空")
    private String name;

    @NotNull(message = "角色信息不能为空")
    private String info;

    List<Integer> menuIds;
}
