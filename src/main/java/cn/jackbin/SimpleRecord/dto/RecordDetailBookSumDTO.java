package cn.jackbin.SimpleRecord.dto;

import lombok.Data;

/**
 * 账本金额合计
 */
@Data
public class RecordDetailBookSumDTO {
    private Integer recordBookId;

    private Double amountTotal;
}
