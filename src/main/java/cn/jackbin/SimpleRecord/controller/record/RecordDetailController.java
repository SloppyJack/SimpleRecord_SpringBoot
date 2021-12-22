package cn.jackbin.SimpleRecord.controller.record;

import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.common.LocalUserId;
import cn.jackbin.SimpleRecord.common.anotations.HandleDict;
import cn.jackbin.SimpleRecord.common.anotations.LoginRequired;
import cn.jackbin.SimpleRecord.constant.RecordConstant;
import cn.jackbin.SimpleRecord.dto.RecordDetailDTO;
import cn.jackbin.SimpleRecord.service.RecordDetailContext;
import cn.jackbin.SimpleRecord.service.impl.ExpendRecordDetail;
import cn.jackbin.SimpleRecord.vo.PageVO;
import cn.jackbin.SimpleRecord.vo.RecordDetailVO;
import cn.jackbin.SimpleRecord.vo.Result;
import cn.jackbin.SimpleRecord.service.RecordDetailService;
import cn.jackbin.SimpleRecord.vo.GetRecordsByMonthVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 账单详情相关接口
 * @date: 2020/6/7 20:24
 **/
@Api(value = "RecordController", tags = { "账单详情访问接口" })
@RestController
@RequestMapping("/recordDetail")
public class RecordDetailController {

    @Autowired
    private RecordDetailService recordDetailService;

    @Autowired
    private RecordDetailContext recordDetailContext;

    @Autowired
    private ExpendRecordDetail expendRecordDetail;

    /**
     * 记账
     */
    @LoginRequired
    @ApiOperation(value = "新增记账记录")
    @PostMapping
    public Result<?> addRecord(@RequestBody @Validated RecordDetailVO vo) {
        Long userId = LocalUserId.get();
        recordDetailContext.addOrEdit(userId.intValue(), vo);
        return Result.success();
    }

    @ApiOperation(value = "修改当前登录用户的记账记录")
    @PutMapping("/{id}")
    public Result<?> updateRecord(@PathVariable("id") @Validated @Positive(message = "id需为正数") Long id, @RequestBody @Validated RecordDetailVO vo) {
        Long userId = LocalUserId.get();
        vo.setId(id);
        recordDetailContext.addOrEdit(userId.intValue(), vo);
        return Result.success();
    }

    @ApiOperation(value = "删除当前登录用户的记账记录")
    @DeleteMapping("/{id}")
    public Result<?> deleteRecord(@PathVariable("id") @Positive(message = "{id}") Integer id) {
        Long userId = LocalUserId.get();
        recordDetailContext.del(userId.intValue(), id);
        return Result.success();
    }

    @HandleDict
    @ApiOperation(value = "分页获取某个月份账本记账记录")
    @PostMapping("/monthBookRecords")
    public Result<?> getMonthBookRecords(@RequestBody  @Validated GetRecordsByMonthVO vo) {
        PageBO<RecordDetailDTO> pageBO = new PageBO<>(vo.getPageNo(), vo.getPageSize());
        Long userId = LocalUserId.get();
        recordDetailService.getMonthBookRecords(vo.getRecordBookId(), userId.intValue(), vo.getMonth(), vo.getOccurTime(), vo.getKeyWord(), pageBO);
        return Result.success(pageBO);
    }

    @HandleDict
    @ApiOperation(value = "分页获取某个月份账户流水")
    @PostMapping("/monthAccountRecords")
    public Result<?> getMonthAccountRecords(@RequestBody  @Validated GetRecordsByMonthVO vo) {
        PageBO<RecordDetailDTO> pageBO = new PageBO<>(vo.getPageNo(), vo.getPageSize());
        Long userId = LocalUserId.get();
        recordDetailService.getMonthAccountRecords(vo.getRecordAccountId(), userId.intValue(), vo.getMonth(), vo.getOccurTime(), vo.getKeyWord(), pageBO);
        return Result.success(pageBO);
    }

    @HandleDict
    @ApiOperation(value = "分页获取某个月份报销记录")
    @PostMapping("/recoverable")
    public Result<?> getRecoverableList(@RequestBody  @Validated PageVO vo) {
        PageBO<RecordDetailDTO> pageBO = new PageBO<>(vo.getPageNo(), vo.getPageSize());
        Long userId = LocalUserId.get();
        recordDetailService.getRecoverableList(userId.intValue(), RecordConstant.TO_RECOVERABLE, pageBO);
        return Result.success(pageBO);
    }

    @ApiOperation(value = "批量报销")
    @PutMapping("/recover")
    public Result<?> recoverRecords(@RequestBody List<Long> ids){
        Long userId = LocalUserId.get();
        expendRecordDetail.recoverRecords(userId.intValue(), ids);
        return Result.success();
    }
}
