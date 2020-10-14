package cn.jackbin.SimpleRecord.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.entity
 * @date: 2020/8/19 19:40
 **/
@Data
public class BaseDO {
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    @JsonFormat(pattern="yyyy-MM hh:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    private Date deleteTime;
}
