package cn.jackbin.SimpleRecord.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.vo
 * @date: 2021/7/21 21:37
 **/
@Data
public class AddDictVO {
    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空")
    private String name;

    /**
     * 字典编码
     */
    @NotBlank(message = "字典编码不能为空")
    private String code;

    private Integer orderNo;

    private String remark;
}
