package cn.jackbin.SimpleRecord.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.io.Serializable;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 字典表
 * @date: 2021/7/13 20:35
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("tb_dict")
public class DictDO extends BaseDO implements Serializable {

    private static final long serialVersionUID = 3248158042590482689L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 字典编码
     */
    private String code;

    /**
     * 状态（0：正常，1：停用）
     */
    private Integer status;

    /**
     * 是否系统内置
     */
    private Integer isSysDefault;

    /**
     * 排序
     */
    private Integer orderNo;

    private String remark;

}
