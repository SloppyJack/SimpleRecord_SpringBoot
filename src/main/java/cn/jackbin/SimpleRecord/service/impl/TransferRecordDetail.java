package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.bo.RecordDetailBO;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.constant.RecordConstant;
import cn.jackbin.SimpleRecord.entity.RecordAccountDO;
import cn.jackbin.SimpleRecord.entity.RecordDetailDO;
import cn.jackbin.SimpleRecord.exception.BusinessException;
import cn.jackbin.SimpleRecord.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 转账记账策略
 * @date: 2021/10/8 20:59
 **/
@Component
public class TransferRecordDetail implements RecordDetailHandler {

    @Autowired
    private RecordDetailFactory factory;

    @Autowired
    private RecordDetailService recordDetailService;

    @Autowired
    private RecordAccountService recordAccountService;

    @Autowired
    private DictItemService dictItemService;

    @PostConstruct
    public void init(){
        factory.addHandler(TRANSFER_TYPE, this);
    }

    @Override
    public void handleAdd(Integer userId, RecordDetailBO bo) {
        // 源账户减去金额
        int sid = recordDetailService.add(userId, bo.getSourceAccountId(), null, bo.getTargetAccountId(), bo.getRecordBookId(), null, bo.getRecordTypeId(),
                "内部转账", -bo.getAmount(), bo.getOccurTime(), null, bo.getRemark(), null);
        // 目标账户增加金额
        int tid = recordDetailService.add(userId, bo.getTargetAccountId(), bo.getSourceAccountId(), null, bo.getRecordBookId(), sid, bo.getRecordTypeId(),
                "内部转账", bo.getAmount(), bo.getOccurTime(), null, bo.getRemark(),null);
        recordDetailService.updateRId((long) sid, tid);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void handleUpdate(RecordDetailBO bo) {
        // 通过rid找到源id
        RecordDetailDO sourceDetailDO = recordDetailService.getByRId(bo.getId().intValue());
        // 更新关联记录
        recordDetailService.update(sourceDetailDO.getId(), bo.getRecordBookId(), -bo.getAmount(), bo.getOccurTime(), bo.getTag(), bo.getRemark());
        // 更新目标记录
        recordDetailService.update(bo.getId(), bo.getRecordBookId(), bo.getAmount(), bo.getOccurTime(), bo.getTag(), bo.getRemark());
    }

    @Override
    public void handleDel(RecordDetailDO recordDetailDO) {
        // 先删除关联记录
        recordDetailService.removeByRId(recordDetailDO.getId());
        recordDetailService.removeById(recordDetailDO.getId());
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void check(Integer userId, RecordDetailBO recordDetailBO) {
        if (recordDetailBO.getSourceAccountId() == null) {
            throw new BusinessException(CodeMsg.SOURCE_ACCOUNT_NOT_NULL);
        }
        // 源账户和目标账户不能同
        if (recordDetailBO.getSourceAccountId().equals(recordDetailBO.getTargetAccountId())) {
            throw new BusinessException(CodeMsg.SOURCE_CANT_EQUAL_TARGET_ACCOUNT);
        }
        // 源账户类型不能为应收应付
        RecordAccountDO sourceAccount = recordAccountService.getById(recordDetailBO.getSourceAccountId());
        String sourceAccountType = dictItemService.getById(sourceAccount.getType()).getValue();
        if (RecordConstant.PAYMENT_ACCOUNT.equals(sourceAccountType)){
            throw new BusinessException(CodeMsg.SOURCE_RECORD_ACCOUNT_NOT_PAYMENT);
        }
        // 目标账户不能为应收/应付账户
        RecordAccountDO targetAccount = recordAccountService.getById(recordDetailBO.getTargetAccountId());
        String targetAccountType = dictItemService.getById(targetAccount.getType()).getValue();
        if (RecordConstant.PAYMENT_ACCOUNT.equals(targetAccountType)){
            throw new BusinessException(CodeMsg.TARGET_RECORD_ACCOUNT_NOT_PAYMENT);
        }
    }
}
