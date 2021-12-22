package cn.jackbin.SimpleRecord.controller.record;

import cn.jackbin.SimpleRecord.common.LocalUser;
import cn.jackbin.SimpleRecord.common.LocalUserId;
import cn.jackbin.SimpleRecord.common.anotations.LoginRequired;
import cn.jackbin.SimpleRecord.constant.RecordConstant;
import cn.jackbin.SimpleRecord.dto.SpendCategoryTotalDTO;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.service.RecordDetailService;
import cn.jackbin.SimpleRecord.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.controller.record
 * @date: 2021/3/16 20:00
 **/
@Api(value = "AnalysisController", tags = { "主页访问接口" })
@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private RecordDetailService recordDetailService;

    @ApiOperation(value = "获取某个月份的支出和收入")
    @GetMapping("/spendTotalInMonth/{date}")
    public Result<?> getSpendTotalInMonth(@ApiParam(required = true, value = "年月（yyyy-MM）") @Validated
                                          @DateTimeFormat(pattern="yyyy-MM") @PathVariable(value = "date") Date date) {
        Long userId = LocalUserId.get();
        List<Double> list = recordDetailService.getSpendTotalByMonth(userId.intValue(), date);
        return Result.success(list);
    }

    @ApiOperation(value = "获取某个月份前三消费类别")
    @GetMapping("/topThreeSpendCategoryTotal/{date}")
    public Result<?> getTopThreeSpendTotal(@ApiParam(required = true, value = "年月（yyyy-MM）") @Validated
                                           @DateTimeFormat(pattern="yyyy-MM") @PathVariable(value = "date")Date date) {
        Long userId = LocalUserId.get();
        List<SpendCategoryTotalDTO> list = recordDetailService.getSpendTotalBySpendCategory(userId.intValue(), RecordConstant.EXPEND_RECORD_TYPE,
                date, 0, 3);
        return Result.success(list);
    }
}
