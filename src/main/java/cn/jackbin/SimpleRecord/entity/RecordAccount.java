package cn.jackbin.SimpleRecord.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 账户表
 * @date: 2021/7/13 21:01
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("tb_record_account")
public class RecordAccount extends BaseDO implements Serializable {

    private static final long serialVersionUID = -7198600840110284750L;

    private Long id;

    private Integer userId;

    /**
     * 账户类型（数据字典维护）
     */
    private Integer type;

    /**
     * 账户名
     */
    private String name;

    /**
     * 是否属于净资产（1：属于，2：不属于）
     */
    private Integer isNetAssets;

    private Integer status;
}
