package cn.jackbin.SimpleRecord.vo;

import lombok.Data;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.vo
 * @date: 2021/7/19 21:39
 **/
@Data
public class GetDictVO extends PageVO {
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


}
