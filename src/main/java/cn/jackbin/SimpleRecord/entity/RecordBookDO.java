package cn.jackbin.SimpleRecord.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 账单表
 * @date: 2021/7/13 21:05
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("tb_record_book")
public class RecordBookDO extends BaseDO implements Serializable {

    private static final long serialVersionUID = -8088393864369379823L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 账本名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否为用户默认
     */
    private Integer isUserDefault;

    /**
     * 排序
     */
    private Integer orderNo;

    private Integer status;
}
