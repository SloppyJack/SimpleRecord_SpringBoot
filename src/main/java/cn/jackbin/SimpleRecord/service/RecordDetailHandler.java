package cn.jackbin.SimpleRecord.service;

import cn.jackbin.SimpleRecord.bo.RecordDetailBO;
import cn.jackbin.SimpleRecord.entity.RecordDetailDO;
import org.springframework.transaction.annotation.Transactional;

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
    void handleAdd(Integer userId, RecordDetailBO recordDetailBO);

    /**
     * 更新记账记录
     */
    void handleUpdate(RecordDetailBO recordDetailBO);

    /**
     * 删除一条记录
     */
    void handleDel(RecordDetailDO recordDetailDO);

    void check(Integer userId, RecordDetailBO recordDetailBO);

}
