package cn.jackbin.SimpleRecord.service;

import cn.jackbin.SimpleRecord.bo.RecordDetailBO;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.constant.CommonConstants;
import cn.jackbin.SimpleRecord.constant.RecordConstant;
import cn.jackbin.SimpleRecord.entity.DictDO;
import cn.jackbin.SimpleRecord.entity.DictItemDO;
import cn.jackbin.SimpleRecord.exception.BusinessException;
import cn.jackbin.SimpleRecord.vo.RecordDetailVO;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private DictItemService dictItemService;

    @Autowired
    private DictService dictService;

    public void addRecord(Integer userId, RecordDetailVO recordDetailVO) {
        RecordDetailHandler handler = factory.getHandler(recordDetailVO.getRecordTypeCode());
        if (handler == null) {
            throw new BusinessException(CodeMsg.BUSINESS_ERROR);
        }
        // 获取dictDO
        DictDO dictDO = dictService.getByCode(RecordConstant.RECORD_TYPE);
        // 从字典获取recordType
        DictItemDO dictItemDO = dictItemService.getByValue(dictDO.getId().intValue(), recordDetailVO.getRecordTypeCode());
        RecordDetailBO recordDetailBO = new RecordDetailBO();
        BeanUtils.copyProperties(recordDetailVO, recordDetailBO);
        // 设置recordTypeId
        recordDetailBO.setRecordTypeId(dictItemDO.getId().intValue());
        handler.handle(userId, recordDetailBO);
    }

    /**
     * 校验记账的数据是否合规
     */
    private void check(Integer userId, Integer sourceAccountId, Integer targetAccountId, Integer recordCategoryId) {

    }
}
