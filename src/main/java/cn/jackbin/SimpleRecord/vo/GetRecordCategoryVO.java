package cn.jackbin.SimpleRecord.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.vo
 * @date: 2021/12/29 20:37
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class GetRecordCategoryVO extends PageVO {
    private String recordTypeCode;
}
