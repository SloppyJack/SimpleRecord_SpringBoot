package cn.jackbin.SimpleRecord.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.vo
 * @date: 2021/8/3 20:05
 **/
@Data
public class EditDictVO {

    @Positive(message = "ID为整数")
    private Integer id;

    /**
     * 字典名称
     */
    @NotNull(message = "名称不能为空")
    private String name;

    /**
     * 字典编码
     */
    @NotNull(message = "编码不能为空")
    private String code;

    @Positive(message = "排序值需大于0")
    private Integer orderNo;

    private String remark;
}
