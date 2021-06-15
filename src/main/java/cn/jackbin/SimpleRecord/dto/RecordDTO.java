package cn.jackbin.SimpleRecord.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: com.example.jianzhang.dto.record
 * @date: 2020/6/7 20:28
 **/
@Data
@NoArgsConstructor
public class RecordDTO {
    @Positive(message = "花费类别为整数")
    private Integer spendCategoryId;

    @NotNull(message = "金额不能为空")
    private Double amount;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date occurTime;

    // 备注
    private String remark;
}
