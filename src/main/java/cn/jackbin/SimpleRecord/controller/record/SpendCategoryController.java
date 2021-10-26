package cn.jackbin.SimpleRecord.controller.record;

import cn.jackbin.SimpleRecord.common.LocalUserId;
import cn.jackbin.SimpleRecord.common.anotations.LoginRequired;
import cn.jackbin.SimpleRecord.entity.RecordUserCategoryDO;
import cn.jackbin.SimpleRecord.service.RecordUserCategoryService;
import cn.jackbin.SimpleRecord.vo.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 用户的记账类别
 * @date: 2021/9/28 21:12
 **/
@Api(value = "RecordUserCategoryController", tags = { "用户记账类别访问接口" })
@RestController
@RequestMapping("/spendCategory")
public class SpendCategoryController {
    @Autowired
    private RecordUserCategoryService userCategoryService;

    @GetMapping
    public Result<?> getSpendCategories() {
        Long userId = LocalUserId.get();
        List<RecordUserCategoryDO> list = userCategoryService.getList(userId.intValue());
        return Result.success(list);
    }
}
