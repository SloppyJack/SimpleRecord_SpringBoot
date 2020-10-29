package cn.jackbin.SimpleRecord.controller.record;

import cn.jackbin.SimpleRecord.bo.MonthRecordBO;
import cn.jackbin.SimpleRecord.dto.RecordDTO;
import cn.jackbin.SimpleRecord.dto.SpendCategoryTotalDTO;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.utils.DateUtil;
import cn.jackbin.SimpleRecord.vo.GetSixMonthRecordsVO;
import cn.jackbin.SimpleRecord.vo.RecordVO;
import cn.jackbin.SimpleRecord.vo.Result;
import cn.jackbin.SimpleRecord.common.LocalUser;
import cn.jackbin.SimpleRecord.constant.RecordConstant;
import cn.jackbin.SimpleRecord.entity.RecordDetailDO;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.service.RecordDetailService;
import cn.jackbin.SimpleRecord.dto.RecordDetailDTO;
import cn.jackbin.SimpleRecord.vo.GetRecordsByMonthVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 记账记录相关接口
 * @date: 2020/6/7 20:24
 **/
@Api(value = "RecordTypeController", tags = { "记账访问接口" })
@RestController
@RequestMapping("/v1/record")
public class RecordController {
    @Autowired
    private RecordDetailService recordDetailService;

    /**
     * 记账
     */
    @ApiOperation(value = "新增记账记录")
    @PostMapping()
    public Result<?> createRecord(@RequestBody @Validated RecordVO vo) {
        RecordDTO dto = new RecordDTO();
        BeanUtils.copyProperties(vo, dto);
        if (recordDetailService.createRecord(dto)) {
            return Result.success();
        } else {
            return Result.error(CodeMsg.INSERT_RECORD_ERROR);
        }
    }

    @ApiOperation(value = "修改当前登录用户的记账记录")
    @PutMapping("/{id}")
    public Result<?> updateRecord(@PathVariable("id") @Positive(message = "{id}") Long id, @RequestBody @Validated RecordVO vo) {
        RecordDetailDO recordDO = recordDetailService.getById(id);
        Result<?> checkResult = checkRecord(recordDO);
        if (checkResult != null) {
            return checkResult;
        }
        RecordDTO dto = new RecordDTO();
        BeanUtils.copyProperties(vo, dto);
        boolean uFlag = recordDetailService.updateRecord(recordDO, dto);
        if (uFlag) {
            return Result.success();
        } else {
            return Result.error(CodeMsg.UPDATE_RECORD_ERROR);
        }
    }

    @ApiOperation(value = "删除当前登录用户的记账记录")
    @DeleteMapping("/{id}")
    public Result<?> deleteBook(@PathVariable("id") @Positive(message = "{id}") Long id) {
        RecordDetailDO recordDO = recordDetailService.getById(id);
        Result<?> checkResult = checkRecord(recordDO);
        if (checkResult != null) {
            return checkResult;
        }
        boolean delFlag = recordDetailService.deleteById(recordDO.getId());
        if (delFlag) {
            return Result.success();
        } else {
            return Result.error(CodeMsg.DEL_RECORD_ERROR);
        }
    }

    @ApiOperation(value = "获取某个月份的支出和收入")
    @GetMapping("/spendTotalInMonth/{date}")
    public Result<?> getSpendTotalInMonth(@ApiParam(required = true, value = "年月（yyyy-MM）") @Validated
                                                  @DateTimeFormat(pattern="yyyy-MM") @PathVariable(value = "date")Date date) {
        UserDO userDO = LocalUser.getLocalUser();
        List<Double> list = recordDetailService.getSpendTotalByMonth(userDO.getId(), date);
        return Result.success(list);
    }

    @ApiOperation(value = "获取某个月份前三消费类别")
    @GetMapping("/topThreeSpendCategoryTotal/{date}")
    public Result<?> getTopThreeSpendTotal(@ApiParam(required = true, value = "年月（yyyy-MM）") @Validated
                                          @DateTimeFormat(pattern="yyyy-MM") @PathVariable(value = "date")Date date) {
        UserDO userDO = LocalUser.getLocalUser();
        List<SpendCategoryTotalDTO> list = recordDetailService.getSpendTotalBySpendCategory(userDO.getId(), RecordConstant.EXPEND_RECORD_TYPE,
                date, 0, 3);
        return Result.success(list);
    }

    @ApiOperation(value = "获取某年所有消费类别的总额")
    @GetMapping("/spendCategoryTotal/{year}/{recordType}")
    public Result<?> getSpendCategoryTotalInYear(@ApiParam(required = true, value = "年（yyyy）") @Validated
                                           @DateTimeFormat(pattern="yyyy") @PathVariable(value = "year")Date date,
                                           @NotNull(message = "记账类型编码不能为空") @PathVariable(value = "recordType")String recordType) {
        if (!recordType.equals(RecordConstant.EXPEND_RECORD_TYPE) && !recordType.equals(RecordConstant.INCOME_RECORD_TYPE)) {
            return Result.error(CodeMsg.RECORD_TYPE_CODE_ERROR);
        }
        UserDO userDO = LocalUser.getLocalUser();
        List<SpendCategoryTotalDTO> list = recordDetailService.getSpendSpendCategoryTotalByYear(userDO.getId(), RecordConstant.EXPEND_RECORD_TYPE,
                date);
        return Result.success(list);
    }


    @ApiOperation(value = "分页获取某个月份记账记录")
    @PostMapping("/listByMonth")
    public Result<?> getListByMonth(@RequestBody  @Validated GetRecordsByMonthVO vo) {
        if (!vo.getRecordTypeCode().equals(RecordConstant.EXPEND_RECORD_TYPE) && !vo.getRecordTypeCode().equals(RecordConstant.INCOME_RECORD_TYPE)) {
            return Result.error(CodeMsg.RECORD_TYPE_CODE_ERROR);
        }
        UserDO userDO = LocalUser.getLocalUser();
        List<RecordDetailDTO> list = recordDetailService.getListByMonth(userDO.getId(), vo.getRecordTypeCode(), vo.getDate(), vo.getPageIndex(), vo.getPageSize());
        return Result.success(list);
    }

    @ApiOperation(value = "获取最近六个月的支出和收入")
    @PostMapping("/latestSixMonthList")
    public Result<?> getLatestSixMonthList(@RequestBody  @Validated GetSixMonthRecordsVO vo) {
        if (!vo.getRecordTypeCode().equals(RecordConstant.EXPEND_RECORD_TYPE) && !vo.getRecordTypeCode().equals(RecordConstant.INCOME_RECORD_TYPE)) {
            return Result.error(CodeMsg.RECORD_TYPE_CODE_ERROR);
        }
        UserDO userDO = LocalUser.getLocalUser();
        List<MonthRecordBO> list = recordDetailService.getLatestSixMonthList(userDO.getId(), vo.getRecordTypeCode(),
                vo.getBeginDate(), DateUtil.addMonth(vo.getBeginDate(), 6));
        return Result.success(list);
    }

    private Result<?> checkRecord(RecordDetailDO recordDetailDO) {
        if (recordDetailDO == null) {
            return Result.error(CodeMsg.NOT_FIND_DATA);
        }
        // 校验是否为当前登录人的记账记录
        UserDO userDO = LocalUser.getLocalUser();
        if (!recordDetailDO.getUserId().equals(userDO.getId().intValue())) {
            return Result.error(CodeMsg.DEL_RECORD_FORBIDDEN);
        }
        return null;
    }
}
