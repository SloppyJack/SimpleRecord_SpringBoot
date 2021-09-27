package cn.jackbin.SimpleRecord.controller.basic;

import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.constant.CommonConstants;
import cn.jackbin.SimpleRecord.entity.DictDO;
import cn.jackbin.SimpleRecord.entity.DictItemDO;
import cn.jackbin.SimpleRecord.exception.BusinessException;
import cn.jackbin.SimpleRecord.service.DictItemService;
import cn.jackbin.SimpleRecord.service.DictService;
import cn.jackbin.SimpleRecord.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 字典相关
 * @date: 2021/7/19 21:21
 **/
@Api(value = "DictController", tags = { "字典相关接口" })
@RestController
@RequestMapping("/dict")
public class DictController {
    @Autowired
    private DictService dictService;
    @Autowired
    private DictItemService dictItemService;

    @PostMapping("/page")
    public Result<?> getList(@RequestBody @Validated GetDictVO vo) {
        PageBO<DictDO> pageBO = new PageBO<>(vo.getPageNo(), vo.getPageSize());
        dictService.getByPage(vo.getName(), vo.getCode(), vo.getStatus(), pageBO);
        return Result.success(pageBO);
    }

    @PostMapping("/add")
    public Result<?> addDict(@RequestBody @Validated AddDictVO vo) {
        // 校验code是否唯一
        if (dictService.getByCode(vo.getCode()) != null ) {
            throw new BusinessException(CodeMsg.DICT_CODE_EXIST);
        }
        dictService.add(vo.getName(), vo.getCode(), vo.getOrderNo(), vo.getRemark());
        return Result.success();
    }

    @PutMapping("/edit")
    public Result<?> editDict(@RequestBody @Validated EditDictVO vo) {
        DictDO dictDO = dictService.getById(vo.getId());
        // 校验记录是否存在
        if (dictDO == null) {
            throw new BusinessException(CodeMsg.NOT_FIND_DATA);
        }
        // 校验code是否唯一
        if (!dictDO.getCode().equals(vo.getCode()) && dictService.getByCode(vo.getCode()) != null ) {
            throw new BusinessException(CodeMsg.DICT_CODE_EXIST);
        }
        checkDict(dictDO);
        dictService.edit(vo.getId(), vo.getName(), vo.getCode(), vo.getOrderNo(), vo.getRemark());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delDict(@PathVariable @Validated @Positive(message = "字典Id需为正数") Integer id) {
        checkDict( dictService.getById(id));
        dictService.removeById(id);
        return Result.success();
    }

    @PostMapping("/dictItem/page")
    public Result<?> getDictData(@RequestBody @Validated GetDictItemVO vo) {
        PageBO<DictItemDO> pageBO = new PageBO<>(vo.getPageNo(), vo.getPageSize());
        dictItemService.getByPage(vo.getDictId(), pageBO);
        return Result.success(pageBO);
    }

    @ApiOperation(value = "恢复字典")
    @PutMapping("/reset/{id}")
    public Result<?> resetDict(@PathVariable @Validated @Positive(message = "Id需为正数") Integer id) {
        dictService.reset(id);
        return Result.success();
    }

    @GetMapping("/dictItems/{dictCode}")
    public Result<?> getDictData(@PathVariable @Validated @NotNull(message = "字典编码不能为空") String dictCode) {
        DictDO dictDO = dictService.getByCode(dictCode);
        if (dictDO == null)
            throw new BusinessException(CodeMsg.NOT_FIND_DATA);
        List<DictItemDO> list = dictItemService.getDictItemsByDictId(dictDO.getId().intValue());
        return Result.success(list);
    }

    public static void checkDict(DictDO dictDO) {
        if (CommonConstants.SYS_DEFAULT == dictDO.getIsSysDefault())
            throw new BusinessException(CodeMsg.CANT_OPERATE_SYS_DATA);
    }

}
