package cn.jackbin.SimpleRecord.controller.record;

import cn.jackbin.SimpleRecord.common.LocalUser;
import cn.jackbin.SimpleRecord.common.anotations.HandleDict;
import cn.jackbin.SimpleRecord.common.anotations.LoginRequired;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.entity.RecordAccountDO;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.exception.BusinessException;
import cn.jackbin.SimpleRecord.service.RecordAccountService;
import cn.jackbin.SimpleRecord.vo.AddRecordAccountVO;
import cn.jackbin.SimpleRecord.vo.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public Result<?> addRecordAccount(@Validated @RequestBody AddRecordAccountVO vo) {
        UserDO userDO = LocalUser.get();
        // check
        if (recordAccountService.getListByUserId(userDO.getId().intValue()).size() > 10) {
            throw new BusinessException(CodeMsg.RECORD_ACCOUNT_SIZE_TOO_MUCH);
        }
        recordAccountService.add(userDO.getId().intValue(), vo.getType(), vo.getName(), vo.getInNetAssets());
        return Result.success();
    }
}
