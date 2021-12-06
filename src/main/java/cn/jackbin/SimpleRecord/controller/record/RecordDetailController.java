package cn.jackbin.SimpleRecord.controller.record;

import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.common.LocalUserId;
import cn.jackbin.SimpleRecord.common.anotations.HandleDict;
import cn.jackbin.SimpleRecord.common.anotations.LoginRequired;
import cn.jackbin.SimpleRecord.dto.RecordDetailDTO;
import cn.jackbin.SimpleRecord.service.RecordDetailContext;
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

    /**
     * 记账
     */
    @LoginRequired
    @ApiOperation(value = "新增记账记录")
    @PostMapping
    public Result<?> addRecord(@RequestBody @Validated RecordDetailVO vo) {
        Long userId = LocalUserId.get();
        recordDetailContext.add(userId.intValue(), vo);
        return Result.success();
    }

    @ApiOperation(value = "修改当前登录用户的记账记录")
    @PutMapping("/{id}")
    public Result<?> updateRecord(@PathVariable("id") @Validated @Positive(message = "id需为正数") Long id, @RequestBody @Validated RecordDetailVO vo) {
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
    @ApiOperation(value = "分页获取某个月份记账记录")
    @PostMapping("/monthList")
    public Result<?> getListByMonth(@RequestBody  @Validated GetRecordsByMonthVO vo) {
        PageBO<RecordDetailDTO> pageBO = new PageBO<>(vo.getPageNo(), vo.getPageSize());
        Long userId = LocalUserId.get();
        recordDetailService.getListByMonth(userId, vo.getMonth(), vo.getOccurTime(), vo.getKeyWord(), pageBO);
        return Result.success(pageBO);
    }
}
