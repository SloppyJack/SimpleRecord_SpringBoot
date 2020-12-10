package cn.jackbin.SimpleRecord.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.vo
 * @date: 2020/12/10 20:01
 **/
@ApiModel(value="AddRoleVO对象", description="添加角色对象")
@Data
public class AddRoleVO {
    @NotNull(message = "名称不能为空")
    public String name;

    @NotNull(message = "描述不能为空")
    public String info;
}
