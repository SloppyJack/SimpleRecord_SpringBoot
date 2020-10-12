package cn.jackbin.SimpleRecord.controller.record;

import cn.jackbin.SimpleRecord.common.LocalUser;
import cn.jackbin.SimpleRecord.dto.CodeMsg;
import cn.jackbin.SimpleRecord.dto.CreateOrUpdateRecordDTO;
import cn.jackbin.SimpleRecord.dto.PageDTO;
import cn.jackbin.SimpleRecord.dto.Result;
import cn.jackbin.SimpleRecord.entity.RecordDetailDO;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.service.RecordDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @PostMapping("/insert")
    public Result<?> createRecord(@RequestBody @Validated CreateOrUpdateRecordDTO dto) {
        if (recordDetailService.createRecord(dto)) {
            return Result.success();
        } else {
            return Result.error(CodeMsg.INSERT_RECORD_ERROR);
        }
    }

    @ApiOperation(value = "分页获取当前登录用户的记账记录")
    @GetMapping("/getUserRecordsByPage")
    public Result<?> getUserRecordsByPage(@Validated PageDTO dto) {
        return Result.success(recordDetailService.getRecordsByLocalUserByPage(dto.getPageIndex(), dto.getPageSize()));
    }

    @ApiOperation(value = "修改当前登录用户的记账记录")
    @PutMapping("/{id}")
    public Result<?> updateRecord(@PathVariable("id") @Positive(message = "{id}") Long id, @RequestBody @Validated CreateOrUpdateRecordDTO validator) {
        RecordDetailDO recordDO = recordDetailService.getById(id);
        Result<?> checkResult = checkRecord(recordDO);
        if (checkResult != null) {
            return checkResult;
        }
        boolean uFlag = recordDetailService.updateRecord(recordDO, validator);
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
    @GetMapping("/getSpendTotalInMonth")
    public Result<?> getSpendTotalInMonth(@ApiParam(required = true, value = "yyyy-MM") @Validated
                                                  @DateTimeFormat(pattern="yyyy-MM") @RequestParam(value = "date")Date date) {
        UserDO userDO = LocalUser.getLocalUser();
        List<Double> list = recordDetailService.getSpendTotalByMonth(userDO.getId(), date);
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
