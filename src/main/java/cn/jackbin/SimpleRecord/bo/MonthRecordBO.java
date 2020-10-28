package cn.jackbin.SimpleRecord.bo;

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
public class MonthRecordBO {
    // 月份
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date occurMonth;

    // 总额
    private Double total = 0.0;
}
