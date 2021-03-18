package cn.jackbin.SimpleRecord.controller.record;

import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.common.ioc.LoginRequired;
import cn.jackbin.SimpleRecord.dto.RecordDTO;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.exception.BusinessException;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 记账记录相关接口
 * @date: 2020/6/7 20:24
 **/
@Api(value = "RecordController", tags = { "记账访问接口" })
@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    private RecordDetailService recordDetailService;

    /**
     * 记账
     */
    @LoginRequired
    @ApiOperation(value = "新增记账记录")
    @PostMapping
    public Result<?> createRecord(@RequestBody @Validated RecordVO vo) {
        RecordDTO dto = new RecordDTO();
        BeanUtils.copyProperties(vo, dto);
        UserDO userDO = LocalUser.get();
        if (recordDetailService.createRecord(dto, userDO.getId())) {
            return Result.success();
        } else {
            return Result.error(CodeMsg.INSERT_RECORD_ERROR);
        }
    }

    @LoginRequired
    @ApiOperation(value = "修改当前登录用户的记账记录")
    @PutMapping("/{id}")
    public Result<?> updateRecord(@PathVariable("id") @Validated @Positive(message = "{id}") Long id, @RequestBody @Validated RecordVO vo) {
        RecordDetailDO recordDO = recordDetailService.getById(id);
        // 校验
        checkRecord(recordDO);
        RecordDTO dto = new RecordDTO();
        BeanUtils.copyProperties(vo, dto);
        boolean uFlag = recordDetailService.updateRecord(recordDO, dto);
        if (uFlag) {
            return Result.success();
        } else {
            return Result.error(CodeMsg.UPDATE_RECORD_ERROR);
        }
    }

    @LoginRequired
    @ApiOperation(value = "删除当前登录用户的记账记录")
    @DeleteMapping("/{id}")
    public Result<?> deleteBook(@PathVariable("id") @Positive(message = "{id}") Long id) {
        RecordDetailDO recordDO = recordDetailService.getById(id);
        checkRecord(recordDO);
        boolean delFlag = recordDetailService.deleteById(recordDO.getId());
        if (delFlag) {
            return Result.success();
        } else {
            return Result.error(CodeMsg.DEL_RECORD_ERROR);
        }
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

    private void checkRecord(RecordDetailDO recordDetailDO) {
        if (recordDetailDO == null) {
            throw new BusinessException(CodeMsg.NOT_FIND_DATA);
        }
        // 校验是否为当前登录人的记账记录
        UserDO userDO = LocalUser.get();
        if (!recordDetailDO.getUserId().equals(userDO.getId().intValue())) {
            throw new BusinessException(CodeMsg.DEL_RECORD_FORBIDDEN);
        }
    }
}
