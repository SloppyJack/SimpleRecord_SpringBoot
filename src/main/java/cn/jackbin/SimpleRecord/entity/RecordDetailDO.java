package cn.jackbin.SimpleRecord.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 记账详情表
 * @date: 2020/9/16 22:19
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("tb_record_detail")
public class RecordDetailDO extends BaseDO implements Serializable {

    private static final long serialVersionUID = 2546476382250156336L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 记账人
     */
    private Integer userId;

    /**
     * 账户Id
     */
    private Integer recordAccountId;

    /**
     * 账本Id
     */
        private Integer recordBookId;

    /**
     * 记账类型
     */
    private Integer recordType;

    /**
     * 分类Id
     */
    private Integer recordCategoryId;

    /**
     * 金额
     */
    private Double amount;

    @JsonFormat(pattern ="yyyy-MM-dd",timezone ="GMT+8")
    private Date occurTime;

    private String tag;

    /**
     * 备注
     */
    private String remark;

    private Integer status;

    /**
     * 是否报销
     */
    private Boolean isRecoverable;
}
