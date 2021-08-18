package cn.jackbin.SimpleRecord.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.vo
 * @date: 2021/8/18 21:26
 **/
@Data
public class EditDictItemVO {
    @Positive(message = "主键ID为整数")
    private Integer id;

    @Positive(message = "字典ID为整数")
    private Integer dictId;

    @NotBlank(message = "字典项文本内容不能为空")
    private String text;

    @NotBlank(message = "字典项值不能为空")
    private String value;

    private String remark;

    private Integer orderNo;
}
