package cn.jackbin.SimpleRecord.controller.record;

import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.common.LocalUser;
import cn.jackbin.SimpleRecord.common.LocalUserId;
import cn.jackbin.SimpleRecord.common.anotations.LoginRequired;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.constant.RecordConstant;
import cn.jackbin.SimpleRecord.dto.RecordBookAnalysisDTO;
import cn.jackbin.SimpleRecord.entity.RecordBookDO;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.exception.BusinessException;
import cn.jackbin.SimpleRecord.service.RecordBookService;
import cn.jackbin.SimpleRecord.vo.AddRecordBookVO;
import cn.jackbin.SimpleRecord.vo.EditRecordBookVO;
import cn.jackbin.SimpleRecord.vo.PageVO;
import cn.jackbin.SimpleRecord.vo.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.controller.record
 * @date: 2021/9/8 19:22
 **/
@Api(value = "RecordBookController", tags = { "账本访问接口" })
@RestController
@RequestMapping("/recordBook")
public class RecordBookController {
    @Autowired
    private RecordBookService recordBookService;

    @PostMapping("/page")
    public Result<?> getPage(@RequestBody @Validated PageVO vo) {
        Long userId = LocalUserId.get();
        PageBO<RecordBookAnalysisDTO> pageBO = new PageBO<>(vo.getPageNo(), vo.getPageSize());
        recordBookService.getByPage(userId.intValue(), pageBO);
        return Result.success(pageBO);
    }

    @PostMapping
    public Result<?> addRecordBook(@RequestBody @Validated AddRecordBookVO vo) {
        Long userId = LocalUserId.get();
        recordBookService.add(userId.intValue(), vo.getName(), vo.getRemark(), vo.getOrderNo());
        return Result.success();
    }


    @DeleteMapping("/{id}")
    public Result<?> delRecordBook(@PathVariable @Validated @Positive(message = "Id需为正数") Integer id) {
        Long userId = LocalUserId.get();
        RecordBookDO recordBookDO = recordBookService.getById(id);
        if (recordBookDO == null || recordBookDO.getUserId() != userId.intValue()) {
            throw new BusinessException(CodeMsg.DEL_RECORD_BOOK_ERROR);
        }
        recordBookService.removeById(id);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<?> editRecordBook(@PathVariable("id") @Validated @Positive(message = "id需为正数") Long id,
                                    @RequestBody @Validated EditRecordBookVO vo) {
        Long userId = LocalUserId.get();
        RecordBookDO defaultBook = recordBookService.getDefaultBook(userId.intValue());
        // 只允许有一个默认账单
        if (defaultBook.getId().equals(id) && !vo.getIsUserDefault()) {
            throw new BusinessException(CodeMsg.ONE_DEFAULT_RECORD_BOOK);
        }
        // 新的默认账单
        if (!defaultBook.getId().equals(id) && vo.getIsUserDefault()) {
            recordBookService.updateDefault(defaultBook.getId(), id, userId.intValue(), vo.getName(), vo.getRemark(), vo.getOrderNo(),
                    vo.getIsUserDefault() ? RecordConstant.USER_DEFAULT : RecordConstant.NOT_USER_DEFAULT);
        } else {
            recordBookService.edit(id, userId.intValue(), vo.getName(), vo.getRemark(), vo.getOrderNo(),
                    vo.getIsUserDefault() ? RecordConstant.USER_DEFAULT : RecordConstant.NOT_USER_DEFAULT);
        }
        return Result.success();
    }

    @GetMapping
    public Result<?> getRecordBooks() {
        Long userId = LocalUserId.get();
        List<RecordBookDO> list = recordBookService.getList(userId.intValue());
        return Result.success(list);
    }
}
