package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.bo.RecordDetailBO;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.constant.RecordConstant;
import cn.jackbin.SimpleRecord.entity.RecordAccountDO;
import cn.jackbin.SimpleRecord.exception.BusinessException;
import cn.jackbin.SimpleRecord.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 支出记账策略
 * @date: 2021/10/8 20:58
 **/
@Component
public class ExpendRecordDetail implements RecordDetailHandler {
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
        factory.addHandler(EXPEND_TYPE, this);
    }

    @Override
    public void handle(Integer userId, RecordDetailBO bo) {
        recordDetailService.add(userId, bo.getTargetAccountId(), bo.getRecordBookId(), bo.getRecordTypeId(), bo.getRecordCategory(),
                bo.getAmount(), bo.getOccurTime(), bo.getTag(), bo.getRemark(), bo.getIsRecoverable());
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
