package cn.jackbin.SimpleRecord.dto;

import cn.jackbin.SimpleRecord.entity.RecordDetailDO;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.vo
 * @date: 2020/10/14 23:02
 **/
@Data
@NoArgsConstructor
public class RecordDetailDTO extends RecordDetailDO {

    /**
     * 花费类别名
     */
    private String spendCategoryName;

    /**
     * 花费类别Code
     */
    private String spendCategoryCode;

}
