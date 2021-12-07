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
 * @description: 收入记账策略
 * @date: 2021/10/8 20:59
 **/
@Component
public class IncomeRecordDetail implements RecordDetailHandler {

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
        factory.addHandler(INCOME_TYPE, this);
    }

    @Override
    public void handleAdd(Integer userId, RecordDetailBO bo) {
        recordDetailService.add(userId, bo.getTargetAccountId(), bo.getRecordBookId(), bo.getRecordTypeId(), bo.getRecordCategory(),
                bo.getAmount(), bo.getOccurTime(), bo.getTag(), bo.getRemark(), null);
    }

    @Override
    public void handleUpdate(RecordDetailBO bo) {
        recordDetailService.update(bo.getId(), bo.getTargetAccountId(), bo.getRecordBookId(), bo.getRecordTypeId(), bo.getRecordCategory(),
                bo.getAmount(), bo.getOccurTime(), bo.getTag(), bo.getRemark(), null);
    }

    @Override
    public void handleDel(RecordDetailDO recordDetailDO) {
        recordDetailService.removeById(recordDetailDO.getId());
    }

    @Override
    public void check(Integer userId, RecordDetailBO recordDetailBO) {
        // 目标账户不能为应收/应付账户
        RecordAccountDO targetAccount = recordAccountService.getById(recordDetailBO.getTargetAccountId());
        String accountType = dictItemService.getById(targetAccount.getType()).getValue();
        if (RecordConstant.PAYMENT_ACCOUNT.equals(accountType)){
            throw new BusinessException(CodeMsg.TARGET_RECORD_ACCOUNT_NOT_PAYMENT);
        }
    }
}
