package cn.jackbin.SimpleRecord.entity;

import cn.jackbin.SimpleRecord.common.anotations.DictValue;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 用户类别表
 * @date: 2021/7/13 21:07
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("tb_record_user_category")
public class RecordUserCategoryDO extends BaseDO implements Serializable {

    private static final long serialVersionUID = -6983888926685534590L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 记账类型
     */
    @DictValue(code = "recordType")
    private Integer type;

    /**
     * 类别名称
     */
    private String name;

    /**
     * 类别编码
     */
    private String code;

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
