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
 * @date: 2020/12/7 21:00
 **/
@ApiModel(value="GetUserVO对象", description="分页获取用户列表对象")
@Data
public class GetUsersVO extends PageVO {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "状态")
    private Boolean deleted;

    @ApiModelProperty(required = true, value = "年月日（yyyy-MM-dd）")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date date;
}
