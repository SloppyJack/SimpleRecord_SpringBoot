package cn.jackbin.SimpleRecord.controller.record;

import cn.jackbin.SimpleRecord.dto.RecordDTO;
import cn.jackbin.SimpleRecord.dto.SpendCategoryTotalDTO;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.vo.RecordVO;
import cn.jackbin.SimpleRecord.vo.Result;
import cn.jackbin.SimpleRecord.common.LocalUser;
import cn.jackbin.SimpleRecord.constant.RecordConstant;
import cn.jackbin.SimpleRecord.entity.RecordDetailDO;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.service.RecordDetailService;
import cn.jackbin.SimpleRecord.dto.RecordDetailDTO;
import cn.jackbin.SimpleRecord.vo.GetRecordsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: com.example.jianzhang.controller.v1
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

    @ApiOperation(value = "分页获取某个月份记账记录")
    @PostMapping("/recordListByMonth")
    public Result<?> getRecordListByMonth(@RequestBody  @Validated GetRecordsVO vo) {
        if (!vo.getRecordTypeCode().equals(RecordConstant.EXPEND_RECORD_TYPE) && !vo.getRecordTypeCode().equals(RecordConstant.INCOME_RECORD_TYPE)) {
            return Result.error(CodeMsg.RECORD_TYPE_CODE_ERROR);
        }
        UserDO userDO = LocalUser.getLocalUser();
        List<RecordDetailDTO> list = recordDetailService.getListByMonth(userDO.getId(), vo.getRecordTypeCode(), vo.getDate(), vo.getPageIndex(), vo.getPageSize());
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
