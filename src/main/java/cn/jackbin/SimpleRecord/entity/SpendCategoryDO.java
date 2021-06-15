package cn.jackbin.SimpleRecord.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 花费类别
 * @date: 2020/9/16 22:20
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("tb_spend_category")
public class SpendCategoryDO extends BaseDO implements Serializable {
    private static final long serialVersionUID1 = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 分类编码
     */
    private String code;

    /**
     * 分类名
     */
    private String name;

    /**
     * 记账类型Id
     */
    private Integer recordTypeId;

    /**
     * 排序
     */
    private Integer orderNo;

    /**
     * 备注
     */
    private String remark;
}
