package cn.jackbin.SimpleRecord.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.dto
 * @date: 2020/9/8 20:41
 **/
@ApiModel(value="PageVO对象", description="分页对象")
@Data
public class PageVO {
    /**
     * 分页的索引
     */
    @ApiModelProperty(required = true, value = "分页的索引")
    @Positive(message = "当前页数须为正数")
    int pageNo;

    /**
     * 分页的大小
     */
    @ApiModelProperty(required = true, value = "分页的大小")
    @Positive(message = "分页的大小须为正数")
    int pageSize;
}
