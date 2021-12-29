package cn.jackbin.SimpleRecord.controller.record;

import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.common.LocalUserId;
import cn.jackbin.SimpleRecord.common.anotations.HandleDict;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.constant.RecordConstant;
import cn.jackbin.SimpleRecord.entity.RecordUserCategoryDO;
import cn.jackbin.SimpleRecord.exception.BusinessException;
import cn.jackbin.SimpleRecord.service.RecordUserCategoryService;
import cn.jackbin.SimpleRecord.vo.GetRecordCategoryVO;
import cn.jackbin.SimpleRecord.vo.RecordCategoryVO;
import cn.jackbin.SimpleRecord.vo.Result;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 用户的记账类别
 * @date: 2021/9/28 21:12
 **/
@Api(value = "RecordUserCategoryController", tags = { "用户记账类别访问接口" })
@RestController
@RequestMapping("/recordCategory")
public class RecordCategoryController {
    @Autowired
    private RecordUserCategoryService userCategoryService;

    @GetMapping
    public Result<?> getRecordCategories() {
        Long userId = LocalUserId.get();
        List<RecordUserCategoryDO> list = userCategoryService.getList(userId.intValue());
        return Result.success(list);
    }

    @HandleDict
    @PostMapping(value = "/page")
    public Result<?> getRecordCategoriesByPage(@RequestBody @Validated GetRecordCategoryVO vo) {
        String recordTypeCode = vo.getRecordTypeCode();
        if (StringUtils.isNoneBlank(recordTypeCode) && !recordTypeCode.equals(RecordConstant.EXPEND_RECORD_TYPE) && !recordTypeCode.equals(RecordConstant.INCOME_RECORD_TYPE)) {
            return Result.error(CodeMsg.RECORD_TYPE_CODE_ERROR);
        }
        PageBO<RecordUserCategoryDO> pageBO = new PageBO<>(vo.getPageNo(), vo.getPageSize());
        Long userId = LocalUserId.get();
        userCategoryService.getByPage(userId.intValue(), recordTypeCode, pageBO);
        return Result.success(pageBO);
    }

    @PutMapping(value = "/reset")
    public Result<?> resetRecordCategories(){
        Long userId = LocalUserId.get();
        userCategoryService.reset(userId.intValue());
        return Result.success();
    }

    @PostMapping
    public Result<?> addRecordCategory(@RequestBody @Validated RecordCategoryVO vo) {
        String recordTypeCode = vo.getRecordTypeCode();
        if (!recordTypeCode.equals(RecordConstant.EXPEND_RECORD_TYPE) && !recordTypeCode.equals(RecordConstant.INCOME_RECORD_TYPE)) {
            return Result.error(CodeMsg.RECORD_TYPE_CODE_ERROR);
        }
        Long userId = LocalUserId.get();
        userCategoryService.saveOrUpdate(null, userId.intValue(), recordTypeCode, vo.getName(), vo.getOrderNo());
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<?> editRecordCategory(@PathVariable("id") @Validated @Positive(message = "id需为正数") Long id,
                                        @RequestBody @Validated RecordCategoryVO vo){
        String recordTypeCode = vo.getRecordTypeCode();
        if (!recordTypeCode.equals(RecordConstant.EXPEND_RECORD_TYPE) && !recordTypeCode.equals(RecordConstant.INCOME_RECORD_TYPE)) {
            return Result.error(CodeMsg.RECORD_TYPE_CODE_ERROR);
        }
        Long userId = LocalUserId.get();
        userCategoryService.saveOrUpdate(id, userId.intValue(), recordTypeCode, vo.getName(), vo.getOrderNo());
        return Result.success();

    }

    @DeleteMapping("/{id}")
    public Result<?> delRecordCategory(@PathVariable("id") @Validated @Positive(message = "id需为正数") Long id){
        Long userId = LocalUserId.get();
        RecordUserCategoryDO recordUserCategoryDO = userCategoryService.getById(id);
        if (recordUserCategoryDO == null || userId.intValue() != recordUserCategoryDO.getUserId()){
            throw new BusinessException(CodeMsg.NOT_FIND_DATA);
        }
        userCategoryService.removeById(id);
        return Result.success();
    }

}
