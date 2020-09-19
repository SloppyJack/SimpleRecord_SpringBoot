package cn.jackbin.SimpleRecord.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.dto
 * @date: 2020/9/8 20:41
 **/
@Data
public class PageDTO {
    /**
     * 分页的索引
     */
    @ApiModelProperty(required = true, value = "分页的索引")
    @NotNull(message = "当前页数须为整数")
    int pageIndex;

    /**
     * 分页的大小
     */
    @ApiModelProperty(required = true, value = "分页的大小")
    @Positive(message = "分页的大小须为整数")
    int pageSize;
}
