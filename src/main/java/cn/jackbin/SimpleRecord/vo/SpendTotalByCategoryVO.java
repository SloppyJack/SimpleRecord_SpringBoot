package cn.jackbin.SimpleRecord.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 根据分类获取的花费总额
 * @date: 2020/10/13 19:46
 **/
@Data
@NoArgsConstructor
public class SpendTotalByCategoryVO {
    /**
     * 花费类别Id
     */
    private Long spendCategoryId;

    /**
     * 花费类别名称
     */
    private String spendCategoryName;

    /**
     * 花费总额
     */
    private Double total;
}
