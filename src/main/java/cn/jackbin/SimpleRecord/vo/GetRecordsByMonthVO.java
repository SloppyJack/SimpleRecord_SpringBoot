package cn.jackbin.SimpleRecord.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.dto
 * @date: 2020/10/14 23:14
 **/
@ApiModel(value="GetRecordsByMonthVO对象", description="获取记账记录对象")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class GetRecordsByMonthVO extends PageVO{

    private Integer recordBookId;

    private Integer recordAccountId;

    @ApiModelProperty(required = true, value = "年月（yyyy-MM）")
    @JsonFormat(pattern="yyyy-MM",timezone = "GMT+8")
    @NotNull
    private Date month;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date occurTime;

    /**
     * 关键词
     */
    private String keyWord;
}
