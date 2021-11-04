package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.bo.RecordDetailBO;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.entity.RecordAccountDO;
import cn.jackbin.SimpleRecord.exception.BusinessException;
import cn.jackbin.SimpleRecord.service.RecordAccountService;
import cn.jackbin.SimpleRecord.service.RecordDetailFactory;
import cn.jackbin.SimpleRecord.service.RecordDetailHandler;
import cn.jackbin.SimpleRecord.service.RecordDetailService;
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

    @PostConstruct
    public void init(){
        factory.addHandler(TRANSFER_TYPE, this);
    }

    @Override
    public void handle(Integer userId, RecordDetailBO bo) {
        RecordAccountDO sourceAccount = recordAccountService.getById(bo.getSourceAccountId());
        RecordAccountDO targetAccount = recordAccountService.getById(bo.getTargetAccountId());
        // 源账户减去金额
        recordDetailService.add(userId, bo.getSourceAccountId(), bo.getRecordBookId(), bo.getRecordTypeId(), "内部转账",
                -bo.getAmount(), bo.getOccurTime(), null, buildSourceRemark(targetAccount.getName(), bo.getAmount()) , null);
        // 目标账户增加金额
        recordDetailService.add(userId, bo.getSourceAccountId(), bo.getRecordBookId(), bo.getRecordTypeId(), "内部转账",
                -bo.getAmount(), bo.getOccurTime(), null, buildSourceRemark(sourceAccount.getName(), bo.getAmount()) , null);
    }

    @Override
    public void check(Integer userId, RecordDetailBO recordDetailBO) {
        if (recordDetailBO.getSourceAccountId() == null) {
            throw new BusinessException(CodeMsg.SOURCE_ACCOUNT_NOT_NULL);
        }
        if (recordDetailBO.getSourceAccountId().equals(recordDetailBO.getTargetAccountId())) {
            throw new BusinessException(CodeMsg.SOURCE_CANT_EQUAL_TARGET_ACCOUNT);
        }
    }

    private String buildSourceRemark(String accountName, Double amount) {
        return "内部转账：转出至账户" + "【" + accountName + "】" + String.format("%.2f", amount) + "元";
    }

    private String buildTargetRemark(String accountName, Double amount) {
        return "内部转账：由账户" + "【" + accountName + "】转入" + String.format("%.2f", amount) + "元";
    }
}
