package cn.jackbin.SimpleRecord.controller.record;

import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.common.LocalUserId;
import cn.jackbin.SimpleRecord.common.anotations.LoginRequired;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.exception.BusinessException;
import cn.jackbin.SimpleRecord.service.RecordDetailContext;
import cn.jackbin.SimpleRecord.vo.RecordDetailVO;
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
import org.springframework.beans.BeanUtils;
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
        recordDetailContext.addRecord(userId.intValue(), vo);
        return Result.success();
    }

    @LoginRequired
    @ApiOperation(value = "修改当前登录用户的记账记录")
    @PutMapping("/{id}")
    public Result<?> updateRecord(@PathVariable("id") @Validated @Positive(message = "id需为正数") Long id, @RequestBody @Validated RecordDetailVO vo) {
        // 校验
        checkRecord(id);
        // todo fixme
//        boolean uFlag = recordDetailService.updateRecord();
        return Result.success();
    }

    @LoginRequired
    @ApiOperation(value = "删除当前登录用户的记账记录")
    @DeleteMapping("/{id}")
    public Result<?> deleteBook(@PathVariable("id") @Positive(message = "{id}") Long id) {
        checkRecord(id);
        boolean delFlag = recordDetailService.deleteById(id);
        return Result.success();
    }


    @LoginRequired
    @ApiOperation(value = "分页获取某个月份记账记录")
    @PostMapping("/listByMonth")
    public Result<?> getListByMonth(@RequestBody  @Validated GetRecordsByMonthVO vo) {
        if (!vo.getRecordTypeCode().equals(RecordConstant.EXPEND_RECORD_TYPE) && !vo.getRecordTypeCode().equals(RecordConstant.INCOME_RECORD_TYPE)) {
            return Result.error(CodeMsg.RECORD_TYPE_CODE_ERROR);
        }
        UserDO userDO = LocalUser.get();
        PageBO<RecordDetailDTO> list = recordDetailService.getListByMonth(userDO.getId(), vo.getRecordTypeCode(), vo.getDate(), vo.getPageIndex(), vo.getPageSize());
        return Result.success(list);
    }

    private void checkRecord(Long id) {
        RecordDetailDO recordDetailDO = recordDetailService.getById(id);
        if (recordDetailDO == null) {
            throw new BusinessException(CodeMsg.NOT_FIND_DATA);
        }
        // 校验是否为当前登录人的记账记录
        UserDO userDO = LocalUser.get();
        if (!recordDetailDO.getUserId().equals(userDO.getId().intValue())) {
            throw new BusinessException(CodeMsg.OPERATE_RECORD_FORBIDDEN);
        }
    }
}
