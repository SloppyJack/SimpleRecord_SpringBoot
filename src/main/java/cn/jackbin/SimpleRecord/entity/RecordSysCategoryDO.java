package cn.jackbin.SimpleRecord.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 系统内置类别表
 * @date: 2021/7/13 21:07
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("tb_record_sys_category")
public class RecordSysCategoryDO extends BaseDO implements Serializable {

    private static final long serialVersionUID = -358045672217191501L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 记账类型
     */
    private Integer type;

    /**
     * 类别名称
     */
    private String name;

    /**
     * 类别Key
     */
    private String key;

    /**
     * 排序
     */
    private Integer orderNo;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否系统内置
     */
    private Integer isSysDefault;

    private String remark;
}
