package cn.jackbin.SimpleRecord.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.vo
 * @date: 2020/10/15 21:49
 **/
@Data
@NoArgsConstructor
public class RecordDetailVO {
    private Long id;

    @Positive(message = "来源账户须为整数")
    private Integer sourceAccountId;

    @Positive(message = "目标账户须为整数")
    private Integer targetAccountId;

    @Positive(message = "账单Id为整数")
    private Integer recordBookId;

    @NotNull(message = "记账类型不能为空")
    private String recordTypeCode;

    @NotBlank(message = "记账类别不能为空")
    private String recordCategory;

    @NotNull(message = "金额不能为空")
    private Double amount;

    @NotNull(message = "日期不能为空")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date occurTime;

    // 是否报销
    private Integer recoverableStatus;

    private String tag;

    private String remark;
}
