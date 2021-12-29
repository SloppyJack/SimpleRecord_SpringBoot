package cn.jackbin.SimpleRecord.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.vo
 * @date: 2020/10/28 20:50
 **/
@Data
@NoArgsConstructor
public class MonthRecordAnalysisDTO {
    // 月份
    private String occurMonth;

    // 总额
    private Double total = 0.0;
}
