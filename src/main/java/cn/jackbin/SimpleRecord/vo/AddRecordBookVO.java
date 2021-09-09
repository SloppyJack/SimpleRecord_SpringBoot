package cn.jackbin.SimpleRecord.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.vo
 * @date: 2021/9/8 19:25
 **/
@Data
public class AddRecordBookVO {

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
     * 排序
     */
    private Integer orderNo;
}
