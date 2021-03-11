package cn.jackbin.SimpleRecord.dto;

import cn.jackbin.SimpleRecord.entity.SpendCategoryDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.dto
 * @date: 2021/3/11 22:00
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SpendCategoryDTO extends SpendCategoryDO {
    private String recordTypeCode;

    private String recordTypeName;
}
