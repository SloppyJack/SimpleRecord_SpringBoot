package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.service.RecordDetailFactory;
import cn.jackbin.SimpleRecord.service.RecordDetailHandler;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 借贷记账策略
 * @date: 2021/10/8 21:00
 **/
public class LoanRecordDetail implements RecordDetailHandler {

    @Autowired
    private RecordDetailFactory factory;

    @PostConstruct
    public void init(){
        factory.addHandler(LOAN_TYPE, this);
    }

    @Override
    public void handle(Integer userId, Integer sourceAccountId, Integer targetAccountId, Integer recordBookId, String recordTypeCode, Integer recordCategoryId, Double amount, String tag, String remark) {

    }
}