package cn.jackbin.SimpleRecord.controller.record;

import cn.jackbin.SimpleRecord.dto.MonthRecordAnalysisDTO;
import cn.jackbin.SimpleRecord.common.LocalUserId;
import cn.jackbin.SimpleRecord.common.anotations.CommonLog;
import cn.jackbin.SimpleRecord.common.enums.BusinessType;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.constant.RecordConstant;
import cn.jackbin.SimpleRecord.dto.SpendCategoryTotalDTO;
import cn.jackbin.SimpleRecord.service.RecordDetailService;
import cn.jackbin.SimpleRecord.utils.DateUtil;
import cn.jackbin.SimpleRecord.vo.GetSixMonthRecordsVO;
import cn.jackbin.SimpleRecord.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.controller.record
 * @date: 2021/3/16 21:05
 **/
@Api(value = "AnalysisController", tags = { "分析访问接口" })
@RestController
@RequestMapping("/analysis")
public class AnalysisController {
    @Autowired
    private RecordDetailService recordDetailService;

    @CommonLog(title = "获取某年所有消费类别的总额", businessType = BusinessType.QUERY)
    @ApiOperation(value = "获取某年所有消费类别的总额")
    @PreAuthorize("hasAuthority('record:analysis:spendCategoryTotal')")
    @GetMapping("/spendCategoryTotal/{year}/{recordType}")
    public Result<?> getSpendCategoryTotalInYear(@ApiParam(required = true, value = "年（yyyy）") @Validated
                                                 @DateTimeFormat(pattern="yyyy") @PathVariable(value = "year") Date date,
                                                 @NotNull(message = "记账类型编码不能为空") @PathVariable(value = "recordType")String recordType) {
        if (!recordType.equals(RecordConstant.EXPEND_RECORD_TYPE) && !recordType.equals(RecordConstant.INCOME_RECORD_TYPE)) {
            return Result.error(CodeMsg.RECORD_TYPE_CODE_ERROR);
        }
        Long userId = LocalUserId.get();
        List<SpendCategoryTotalDTO> list = recordDetailService.getSpendSpendCategoryTotalByYear(userId.intValue(), recordType,
                date);
        return Result.success(list);
    }

    @CommonLog(title = "获取最近六个月的支出和收入", businessType = BusinessType.QUERY)
    @ApiOperation(value = "获取最近六个月的支出和收入")
    @PreAuthorize("hasAuthority('record:analysis:latestSixMonthList')")
    @PostMapping("/latestSixMonthList")
    public Result<?> getLatestSixMonthList(@RequestBody @Validated GetSixMonthRecordsVO vo) {
        if (!vo.getRecordTypeCode().equals(RecordConstant.EXPEND_RECORD_TYPE) && !vo.getRecordTypeCode().equals(RecordConstant.INCOME_RECORD_TYPE)) {
            return Result.error(CodeMsg.RECORD_TYPE_CODE_ERROR);
        }
        Long userId = LocalUserId.get();
        List<MonthRecordAnalysisDTO> list = recordDetailService.getLatestSixMonthList(userId.intValue(), vo.getRecordTypeCode(),
                vo.getBeginDate(), DateUtil.addMonth(vo.getBeginDate(), 6));
        return Result.success(list);
    }
}
