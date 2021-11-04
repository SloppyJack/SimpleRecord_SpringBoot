package cn.jackbin.SimpleRecord.service;

import cn.jackbin.SimpleRecord.bo.RecordDetailBO;
import cn.jackbin.SimpleRecord.vo.RecordDetailVO;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 记账处理
 * @date: 2021/9/29 23:06
 **/
public interface RecordDetailHandler {

    String EXPEND_TYPE = "expendType"; // 支出
    String INCOME_TYPE = "incomeType"; // 收入
    String TRANSFER_TYPE = "transferType"; // 转账
    String LOAN_TYPE = "loanType"; // 借贷

    /**
     * 记一笔
     */
    void handle(Integer userId, RecordDetailBO recordDetailBO);

    void check(Integer userId, RecordDetailBO recordDetailBO);

}
