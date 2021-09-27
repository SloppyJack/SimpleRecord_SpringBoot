package cn.jackbin.SimpleRecord.controller.record;

import cn.jackbin.SimpleRecord.common.LocalUser;
import cn.jackbin.SimpleRecord.common.anotations.HandleDict;
import cn.jackbin.SimpleRecord.common.anotations.LoginRequired;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.constant.RecordConstant;
import cn.jackbin.SimpleRecord.entity.RecordAccountDO;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.exception.BusinessException;
import cn.jackbin.SimpleRecord.service.RecordAccountService;
import cn.jackbin.SimpleRecord.vo.RecordAccountVO;
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
 * @date: 2021/8/25 20:50
 **/
@Api(value = "RecordAccountController", tags = { "记账账户访问接口" })
@RestController
@RequestMapping("/recordAccount")
public class RecordAccountController {
    @Autowired
    private RecordAccountService recordAccountService;

    @HandleDict
    @LoginRequired
    @GetMapping("/list")
    public Result<?> getList() {
        List<RecordAccountDO> list = recordAccountService.list();
        return Result.success(list);
    }

    @LoginRequired
    @PostMapping
    public Result<?> addRecordAccount(@Validated @RequestBody RecordAccountVO vo) {
        UserDO userDO = LocalUser.get();
        // check
        if (recordAccountService.getListByUserId(userDO.getId().intValue()).size() > 10) {
            throw new BusinessException(CodeMsg.RECORD_ACCOUNT_SIZE_TOO_MUCH);
        }
        recordAccountService.add(userDO.getId().intValue(), vo.getType(), vo.getName(),
                vo.getInNetAssets() ? RecordConstant.IN_NET_ASSETS : RecordConstant.NOT_IN_NET_ASSETS);
        return Result.success();
    }

    @LoginRequired
    @PutMapping("/{id}")
    public Result<?> editRecordAccount(@PathVariable("id") @Validated @Positive(message = "id需为正数") Long id,  @Validated @RequestBody RecordAccountVO vo) {
        checkOperateRecordAccount(id);
        RecordAccountDO recordAccountDO = new RecordAccountDO();
        recordAccountDO.setId(id);
        recordAccountDO.setType(vo.getType());
        recordAccountDO.setName(vo.getName());
        recordAccountDO.setInNetAssets(vo.getInNetAssets() ? RecordConstant.IN_NET_ASSETS : RecordConstant.NOT_IN_NET_ASSETS);
        recordAccountService.updateById(recordAccountDO);
        return Result.success();
    }

    @LoginRequired
    @DeleteMapping("/{id}")
    public Result<?> deleteRecordAccount(@PathVariable("id") @Validated @Positive(message = "id需为正数") Long id) {
        checkOperateRecordAccount(id);
        recordAccountService.removeById(id);
        return Result.success();
    }

    /**
     * 校验
     */
    private void checkOperateRecordAccount(Long id) {
        UserDO userDO = LocalUser.get();
        RecordAccountDO originDO = recordAccountService.getById(id);
        // check
        if (originDO == null) {
            throw new BusinessException(CodeMsg.NOT_FIND_DATA);
        }
        if (originDO.getUserId() != userDO.getId().intValue()) {
            throw new BusinessException(CodeMsg.OPERATE_RECORD_ACCOUNT_FORBIDDEN);
        }
    }
}
