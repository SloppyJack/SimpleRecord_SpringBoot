package cn.jackbin.SimpleRecord.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.vo
 * @date: 2021/8/25 21:02
 **/
@Data
public class RecordAccountVO {
    @Positive(message = "记账账户类型需为正数")
    private Integer type;

    @NotNull(message = "名称不能为空")
    private String name;

    @NotNull(message = "是否属于尽资产必填")
    private Boolean inNetAssets;

    private Integer orderNo;
}
