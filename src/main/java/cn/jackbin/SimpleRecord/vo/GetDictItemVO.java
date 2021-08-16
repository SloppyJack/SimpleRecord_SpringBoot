package cn.jackbin.SimpleRecord.vo;

import lombok.Data;

import javax.validation.constraints.Positive;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.vo
 * @date: 2021/8/16 20:22
 **/
@Data
public class GetDictItemVO extends PageVO {

    @Positive(message = "字典Id需为正数")
    private Integer dictId;

    private Integer status;
}
