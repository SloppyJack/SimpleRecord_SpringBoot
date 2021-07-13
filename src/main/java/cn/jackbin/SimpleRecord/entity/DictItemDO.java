package cn.jackbin.SimpleRecord.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 字典项表
 * @date: 2021/7/13 20:57
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("tb_dict_item")
public class DictItemDO extends BaseDO implements Serializable {

    private static final long serialVersionUID = 8957367407235942454L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 字典Id
     */
    private Integer dictId;

    /**
     * 字典项文本内容
     */
    private String text;

    /**
     * 字典项值
     */
    private String value;

    /**
     * 备注
     */
    private String remark;

    private Integer orderNo;

    private Integer status;
}
