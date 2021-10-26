package cn.jackbin.SimpleRecord.service;

import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 记账策略控制
 * @date: 2021/10/8 21:02
 **/
@Component
public class RecordDetailContext {

    @Autowired
    private RecordDetailFactory factory;

    public void addRecord(Integer userId, Integer sourceAccountId, Integer targetAccountId, Integer recordBookId, String recordTypeCode,
                          Integer recordCategoryId, Double amount, String tag, String remark) {
        RecordDetailHandler handler = factory.getHandler(recordTypeCode);
        if (handler == null) {
            throw new BusinessException(CodeMsg.BUSINESS_ERROR);
        }
        handler.handle(userId, sourceAccountId, targetAccountId, recordBookId, recordTypeCode, recordCategoryId, amount, tag, remark);
    }

    /**
     * 校验记账的数据是否合规
     */
    private void check(Integer userId, Integer sourceAccountId, Integer targetAccountId, Integer recordCategoryId) {

    }
}
