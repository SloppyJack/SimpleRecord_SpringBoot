package cn.jackbin.SimpleRecord.dto;

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
public class CreateOrUpdateRecordDTO {
    @Positive(message = "花费类别为整数")
    private Long spendCategoryId;

    @NotNull(message = "金额不能为空")
    private Double amount;

    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date occurTime;

    // 备注
    private String remarks;
}
