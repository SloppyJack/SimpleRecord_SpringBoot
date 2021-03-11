package cn.jackbin.SimpleRecord.controller.record;

import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.dto.SpendCategoryDTO;
import cn.jackbin.SimpleRecord.vo.Result;
import cn.jackbin.SimpleRecord.entity.SpendCategoryDO;
import cn.jackbin.SimpleRecord.service.SpendCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: com.example.jianzhang.controller.v1
 * @date: 2020/6/10 21:19
 **/
@Api(value = "SpendCategoryController", tags = { "花费类别访问接口" })
@RestController
@RequestMapping("/v1/spendCategory")
public class SpendCategoryController {
    @Autowired
    private SpendCategoryService spendCategoryService;

    @ApiOperation(value = "获取所有花费类别")
    @GetMapping
    public Result<?> getSpendCategoryList() {
        List<SpendCategoryDTO> list = spendCategoryService.findAll();
        return Result.success(groupCategories(list));
    }

    @ApiOperation(value = "通过记账类型Id获取花费类别")
    @GetMapping("/recordTypeId/{recordTypeId}")
    public Result<?> getByRecordType(@ApiParam(required = true, value = "记账类型Id") @Validated
                              @Positive(message = "记账类型Id为整数") @PathVariable(value = "recordTypeId")  int id) {
        List<SpendCategoryDO> list = spendCategoryService.getByRecordTypeId(id);
        if (list == null) {
            return Result.error(CodeMsg.NOT_FIND_DATA);
        }
        return Result.success(list);
    }

    // 将花费类别分组
    private List<GroupSpendCategory> groupCategories(List<SpendCategoryDTO> list) {
        List<GroupSpendCategory> ret = new ArrayList<>();
        // 字典记录出现的位置
        Map<String, Integer> map = new HashMap<>();
        int i=0;
        for (SpendCategoryDTO temp : list) {
            // 字典中出现过
            if (map.containsKey(temp.getRecordTypeName())) {
                int index = map.get(temp.getRecordTypeName());
                List<SpendCategoryDO> tempList = ret.get(index).getList();
                tempList.add(temp);
            } else {
                map.put(temp.getRecordTypeName(), i);
                List<SpendCategoryDO> l = new ArrayList<>();
                l.add(temp);
                ret.add(new GroupSpendCategory(temp.getRecordTypeCode(), temp.getRecordTypeName(), l));
                i++;
            }
        }
        return ret;
    }

    @Data
    @AllArgsConstructor
    static class GroupSpendCategory {
        private String recordTypeCode;

        private String recordTypeName;

        List<SpendCategoryDO> list;
    }
}
