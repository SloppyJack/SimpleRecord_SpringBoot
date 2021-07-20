package cn.jackbin.SimpleRecord.controller.basic;

import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.entity.DictDO;
import cn.jackbin.SimpleRecord.service.DictService;
import cn.jackbin.SimpleRecord.vo.GetDictVO;
import cn.jackbin.SimpleRecord.vo.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 字典相关
 * @date: 2021/7/19 21:21
 **/
@Api(value = "CommonLogController", tags = { "日志相关接口" })
@RestController
@RequestMapping("/dict")
public class DictController {
    @Autowired
    private DictService dictService;

    @PostMapping("/page")
    public Result<?> getList(@RequestBody @Validated GetDictVO vo) {
        PageBO<DictDO> pageBO = new PageBO<>(vo.getPageNo(), vo.getStatus());
        dictService.getByPage(vo.getName(), vo.getCode(), vo.getStatus(), pageBO);
        return Result.success(pageBO);
    }
}
