package cn.jackbin.SimpleRecord.service;

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
     * @param userId 用户Id
     * @param sourceAccountId 原始账户
     * @param targetAccountId 目标账户
     * @param recordTypeCode 记账类型Code
     * @param recordCategoryId 记账类别
     * @param amount 金额
     * @param tag 标签
     * @param remark 备注
     */
    void handle(Integer userId, Integer sourceAccountId, Integer targetAccountId, Integer recordBookId, String recordTypeCode,
                Integer recordCategoryId, Double amount, String tag, String remark);

}
