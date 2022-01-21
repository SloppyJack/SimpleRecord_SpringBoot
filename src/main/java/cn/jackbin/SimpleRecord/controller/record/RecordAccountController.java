package cn.jackbin.SimpleRecord.controller.record;

import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.common.LocalUser;
import cn.jackbin.SimpleRecord.common.LocalUserId;
import cn.jackbin.SimpleRecord.common.anotations.CommonLog;
import cn.jackbin.SimpleRecord.common.anotations.HandleDict;
import cn.jackbin.SimpleRecord.common.anotations.LoginRequired;
import cn.jackbin.SimpleRecord.common.enums.BusinessType;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.constant.RecordConstant;
import cn.jackbin.SimpleRecord.dto.RecordAccountAnalysisDTO;
import cn.jackbin.SimpleRecord.entity.RecordAccountDO;
import cn.jackbin.SimpleRecord.entity.RecordDetailDO;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.exception.BusinessException;
import cn.jackbin.SimpleRecord.service.RecordAccountService;
import cn.jackbin.SimpleRecord.service.RecordDetailService;
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
    @Autowired
    private RecordDetailService recordDetailService;

    @HandleDict
    @GetMapping
    public Result<?> getList() {
        List<RecordAccountDO> list = recordAccountService.getListByUserId(LocalUserId.get().intValue());
        return Result.success(list);
    }

    @CommonLog(title = "添加记账账户", businessType = BusinessType.INSERT)
    @PostMapping
    public Result<?> addRecordAccount(@Validated @RequestBody RecordAccountVO vo) {
        Long userId = LocalUserId.get();
        // check
        if (recordAccountService.getListByUserId(userId.intValue()).size() > 10) {
            throw new BusinessException(CodeMsg.RECORD_ACCOUNT_SIZE_TOO_MUCH);
        }
        // 账户名不能重复
        if (recordAccountService.getByName(userId.intValue(), vo.getName()) != null){
            throw new BusinessException(CodeMsg.RECORD_ACCOUNT_NAME_REPEAT);
        }
        recordAccountService.add(userId.intValue(), vo.getType(), vo.getName(),
                vo.getInNetAssets() ? RecordConstant.BUSINESS_YES : RecordConstant.BUSINESS_NOT, vo.getOrderNo());
        return Result.success();
    }

    @CommonLog(title = "修改记账账户", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}")
    public Result<?> editRecordAccount(@PathVariable("id") @Validated @Positive(message = "id需为正数") Long id,  @Validated @RequestBody RecordAccountVO vo) {
        Long userId = LocalUserId.get();
        // check
        checkOperateRecordAccount(id, userId);
        // 账户名不能重复
        RecordAccountDO updateRecordAccount = recordAccountService.getByName(userId.intValue(), vo.getName());
        if (updateRecordAccount != null && id.intValue() != updateRecordAccount.getId().intValue()){
            throw new BusinessException(CodeMsg.RECORD_ACCOUNT_NAME_REPEAT);
        }
        recordAccountService.update(id, vo.getType(), vo.getName(), vo.getInNetAssets() ? RecordConstant.BUSINESS_YES : RecordConstant.BUSINESS_NOT,
                vo.getOrderNo());
        return Result.success();
    }

    @CommonLog(title = "删除记账账户", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public Result<?> deleteRecordAccount(@PathVariable("id") @Validated @Positive(message = "id需为正数") Long id) {
        Long userId = LocalUserId.get();
        checkOperateRecordAccount(id, userId);
        PageBO<RecordDetailDO> pageBO = new PageBO<>(1, 1);
        recordDetailService.getListByRecordAccountId(userId.intValue(), id.intValue(), pageBO);
        if (pageBO.getTotal() > 0){
            throw new BusinessException(CodeMsg.RECORD_ACCOUNT_RELATED);
        }
        recordAccountService.removeById(id);
        return Result.success();
    }

    @CommonLog(title = "获取资产分析", businessType = BusinessType.QUERY)
    @HandleDict
    @GetMapping("/statistics")
    public Result<?> accountStatistics() {
        Long userId = LocalUserId.get();
        List<RecordAccountAnalysisDTO> list = recordAccountService.analysisAccounts(userId.intValue());
        return Result.success(list);
    }

    /**
     * 校验
     */
    private void checkOperateRecordAccount(Long id, Long userId) {
        RecordAccountDO originDO = recordAccountService.getById(id);
        // check
        if (originDO == null) {
            throw new BusinessException(CodeMsg.NOT_FIND_DATA);
        }
        if (originDO.getUserId() != userId.intValue()) {
            throw new BusinessException(CodeMsg.OPERATE_RECORD_ACCOUNT_FORBIDDEN);
        }
    }
}
