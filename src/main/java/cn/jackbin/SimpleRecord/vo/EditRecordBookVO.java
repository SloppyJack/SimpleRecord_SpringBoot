package cn.jackbin.SimpleRecord.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.vo
 * @date: 2021/9/8 19:43
 **/
@Data
public class EditRecordBookVO {

    /**
     * 账本名称
     */
    @NotNull(message = "账本名称不能为空")
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否为用户默认
     */
    @NotNull(message = "是否默认账本不能为空")
    private Boolean isUserDefault;

    /**
     * 排序
     */
    private Integer orderNo;
}
