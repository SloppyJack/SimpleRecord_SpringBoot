package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.bo.RecordDetailBO;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.constant.RecordConstant;
import cn.jackbin.SimpleRecord.entity.DictDO;
import cn.jackbin.SimpleRecord.entity.DictItemDO;
import cn.jackbin.SimpleRecord.entity.RecordAccountDO;
import cn.jackbin.SimpleRecord.entity.RecordDetailDO;
import cn.jackbin.SimpleRecord.exception.BusinessException;
import cn.jackbin.SimpleRecord.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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
    private DictService dictService;

    @Autowired
    private DictItemService dictItemService;

    @PostConstruct
    public void init(){
        factory.addHandler(EXPEND_TYPE, this);
    }

    @Override
    public void handleAdd(Integer userId, RecordDetailBO bo) {
        recordDetailService.add(userId, bo.getTargetAccountId(), bo.getRecordBookId(), bo.getRecordTypeId(), bo.getRecordCategory(),
                -bo.getAmount(), bo.getOccurTime(), bo.getTag(), bo.getRemark(), bo.getRecoverableStatus());
    }

    @Override
    public void handleUpdate(RecordDetailBO bo) {
        recordDetailService.update(bo.getId(), bo.getTargetAccountId(), bo.getRecordBookId(), bo.getRecordTypeId(), bo.getRecordCategory(),
                -bo.getAmount(), bo.getOccurTime(), bo.getTag(), bo.getRemark(), bo.getRecoverableStatus());
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

    /**
     * 批量报销
     */
    @Transactional
    public void recoverRecords(Integer userId, List<Long> ids){
        if (ids.size() == 0)
            throw new BusinessException(CodeMsg.PARAMETER_ILLEGAL);
        // 收集所有待报销的记录
        List<RecordDetailDO> list = recordDetailService.listByIds(ids).stream()
                .filter(n -> n.getRecoverableStatus() == RecordConstant.TO_RECOVERABLE).collect(Collectors.toList());
        RecordDetailDO recordDetailDO = list.stream().filter(n -> n.getUserId() != userId.intValue()).findFirst().orElse(null);
        if (list.size() != ids.size() || recordDetailDO != null){
            throw new BusinessException(CodeMsg.NOT_FIND_DATA);
        }
        // 获取dictDO
        DictDO dictDO = dictService.getByCode(RecordConstant.RECORD_TYPE);
        // 从字典获取recordType
        DictItemDO dictItemDO = dictItemService.getByValue(dictDO.getId().intValue(), INCOME_TYPE);
        AtomicReference<Double> total = new AtomicReference<>(0.0);
        AtomicInteger count = new AtomicInteger();
        list.forEach(n -> {
            total.updateAndGet(v -> v + Math.abs(n.getAmount()));
            count.getAndIncrement();
        });
        RecordDetailDO recoverableRecord = new RecordDetailDO();
        recoverableRecord.setUserId(userId);
        recoverableRecord.setRecordAccountId(list.get(0).getRecordAccountId());
        recoverableRecord.setRecordBookId(list.get(0).getRecordBookId());
        recoverableRecord.setRecordType(dictItemDO.getId().intValue());
        recoverableRecord.setRecordCategory(RecordConstant.BXK);
        recoverableRecord.setAmount(Math.abs(total.get()));
        recoverableRecord.setOccurTime(new Date());
        recoverableRecord.setRemark(buildRecoverableRemark(count.get(), total.get()));
        recordDetailService.save(recoverableRecord);
        // 修改为已报销的状态
        list.forEach(n -> n.setRecoverableStatus(RecordConstant.IS_RECOVERABLE));
        recordDetailService.updateBatchById(list);

    }

    private String buildRecoverableRemark(int count, double amount){
        return "报销款：共报销" + count + "笔账单，总金额为" + amount + "¥";
    }
}
