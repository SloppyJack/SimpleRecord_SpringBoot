package cn.jackbin.SimpleRecord.controller.record;

import cn.jackbin.SimpleRecord.vo.Result;
import cn.jackbin.SimpleRecord.entity.RecordTypeDO;
import cn.jackbin.SimpleRecord.service.RecordTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: com.example.jianzhang.controller.v1
 * @date: 2020/6/9 20:32
 **/
@Api(value = "RecordTypeController", tags = { "记账类别访问接口" })
@RestController
@RequestMapping("/v1/recordType")
public class RecordTypeController {
    @Autowired
    private RecordTypeService recordTypeService;

    @ApiOperation(value = "获取所有记账类别")
    @GetMapping
    public Result<?> getRecordTypes() {
        List<RecordTypeDO> recordTypes = recordTypeService.findAll();
        return Result.success(recordTypes);
    }
}
