package cn.jackbin.SimpleRecord.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 花费类别的总额
 * @date: 2020/10/15 21:31
 **/
@Data
@Builder
public class SpendCategoryTotalDTO {

    /**
     * 花费类别名称
     */
    private String recordCategory;

    /**
     * 花费总额
     */
    private Double total;
}
