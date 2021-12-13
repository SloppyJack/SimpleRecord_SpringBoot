package cn.jackbin.SimpleRecord.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long id;

    private Integer sourceAccountId;

    private Integer targetAccountId;

    private Integer recordBookId;

    private String recordTypeCode;

    private Integer recordTypeId;

    private String recordCategory;

    private Double amount;

    private Date occurTime;

    private Integer recoverableStatus;

    private String tag;

    private String remark;
}
