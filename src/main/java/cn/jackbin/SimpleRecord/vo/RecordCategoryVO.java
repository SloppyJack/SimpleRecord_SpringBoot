package cn.jackbin.SimpleRecord.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.vo
 * @date: 2021/12/29 21:09
 **/
@Data
public class RecordCategoryVO {
    @NotBlank(message = "类别编码不能为空")
    private String recordTypeCode;

    @NotBlank(message = "类别名称不能为空")
    private String name;

    private Integer orderNo;
}
