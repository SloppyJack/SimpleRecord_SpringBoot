package cn.jackbin.SimpleRecord.controller.record;

import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.common.LocalUser;
import cn.jackbin.SimpleRecord.common.anotations.LoginRequired;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.constant.RecordConstant;
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

    @LoginRequired
    @PostMapping("/page")
    public Result<?> getPage(@RequestBody @Validated PageVO vo) {
        UserDO userDO = LocalUser.get();
        PageBO<RecordBookDO> pageBO = new PageBO<>(vo.getPageNo(), vo.getPageSize());
        recordBookService.getByPage(userDO.getId().intValue(), pageBO);
        return Result.success(pageBO);
    }

    @LoginRequired
    @PostMapping
    public Result<?> addRecordBook(@RequestBody @Validated AddRecordBookVO vo) {
        UserDO userDO = LocalUser.get();
        recordBookService.add(userDO.getId().intValue(), vo.getName(), vo.getRemark(), vo.getOrderNo());
        return Result.success();
    }

    @LoginRequired
    @DeleteMapping("/{id}")
    public Result<?> delRecordBook(@PathVariable @Validated @Positive(message = "Id需为正数") Integer id) {
        UserDO userDO = LocalUser.get();
        RecordBookDO recordBookDO = recordBookService.getById(id);
        if (recordBookDO == null || recordBookDO.getUserId() != userDO.getId().intValue()) {
            throw new BusinessException(CodeMsg.DEL_RECORD_BOOK_ERROR);
        }
        recordBookService.removeById(id);
        return Result.success();
    }

    @LoginRequired
    @PutMapping("/{id}")
    public Result<?> editRecordBook(@PathVariable("id") @Validated @Positive(message = "id需为正数") Long id,
                                    @RequestBody @Validated EditRecordBookVO vo) {
        UserDO userDO = LocalUser.get();
        RecordBookDO defaultBook = recordBookService.getDefaultBook(userDO.getId().intValue());
        // 只允许有一个默认账单
        if (defaultBook.getId().equals(id) && !vo.getIsUserDefault()) {
            throw new BusinessException(CodeMsg.ONE_DEFAULT_RECORD_BOOK);
        }
        // 新的默认账单
        if (!defaultBook.getId().equals(id) && vo.getIsUserDefault()) {
            recordBookService.updateDefault(defaultBook.getId(), id, userDO.getId().intValue(), vo.getName(), vo.getRemark(), vo.getOrderNo(),
                    vo.getIsUserDefault() ? RecordConstant.USER_DEFAULT : RecordConstant.NOT_USER_DEFAULT);
        } else {
            recordBookService.edit(id, userDO.getId().intValue(), vo.getName(), vo.getRemark(), vo.getOrderNo(),
                    vo.getIsUserDefault() ? RecordConstant.USER_DEFAULT : RecordConstant.NOT_USER_DEFAULT);
        }
        return Result.success();
    }
}
