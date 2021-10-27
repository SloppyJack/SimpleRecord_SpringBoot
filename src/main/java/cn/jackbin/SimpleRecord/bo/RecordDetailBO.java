package cn.jackbin.SimpleRecord.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.bo
 * @date: 2021/10/26 21:32
 **/
@Data
@NoArgsConstructor
public class RecordDetailBO {
    private Integer sourceAccountId;

    private Integer targetAccountId;

    private Integer recordBookId;

    private String recordTypeCode;

    private Integer recordTypeId;

    private Integer recordCategoryId;

    private Double amount;

    private Date occurTime;

    private String tag;

    private String remark;
}
