package cn.jackbin.SimpleRecord.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Positive;
import java.util.Date;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.vo
 * @date: 2020/12/24 20:36
 **/
@ApiModel(value="GetMenusVO对象", description="分页获取菜单列表对象")
@Data
public class GetMenusVO {
    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "状态")
    private Boolean deleted;

    @ApiModelProperty(required = true, value = "年月日（yyyy-MM-dd）")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date date;

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
