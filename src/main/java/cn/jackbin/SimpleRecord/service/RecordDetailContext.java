package cn.jackbin.SimpleRecord.service;

import cn.jackbin.SimpleRecord.bo.RecordDetailBO;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.constant.RecordConstant;
import cn.jackbin.SimpleRecord.entity.*;
import cn.jackbin.SimpleRecord.exception.BusinessException;
import cn.jackbin.SimpleRecord.vo.RecordDetailVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private RecordAccountService recordAccountService;
    @Autowired
    private RecordBookService recordBookService;
    @Autowired
    private RecordDetailService recordDetailService;

    /**
     * 新增记账记录
     */
    @Transactional
    public void addOrEdit(Integer userId, RecordDetailVO vo) {
        RecordDetailHandler handler = factory.getHandler(vo.getRecordTypeCode());
        if (handler == null) {
            throw new BusinessException(CodeMsg.BUSINESS_ERROR);
        }
        // 获取dictDO
        DictDO dictDO = dictService.getByCode(RecordConstant.RECORD_TYPE);
        // 从字典获取recordType
        DictItemDO dictItemDO = dictItemService.getByValue(dictDO.getId().intValue(), vo.getRecordTypeCode());
        RecordDetailBO bo = new RecordDetailBO();
        BeanUtils.copyProperties(vo, bo);
        // 设置recordTypeId
        bo.setRecordTypeId(dictItemDO.getId().intValue());
        beforeHandle(vo.getId(), userId, bo.getTargetAccountId(), bo.getRecordBookId());
        handler.check(userId, bo);
        // 如果有id就是编辑
        if (vo.getId() != null){
            handler.handleUpdate(bo);
        }else {
            handler.handleAdd(userId, bo);
        }
    }

    @Transactional
    public void del(Integer userId, Integer id) {
        RecordDetailDO detail = recordDetailService.getById(id);
        // 从字典获取recordType
        DictItemDO dictItemDO = dictItemService.getById(detail.getRecordType());
        RecordDetailHandler handler = factory.getHandler(dictItemDO.getValue());
        if (handler == null) {
            throw new BusinessException(CodeMsg.BUSINESS_ERROR);
        }
        if (!userId.equals(detail.getUserId())){
            throw new BusinessException(CodeMsg.OPERATE_RECORD_ACCOUNT_FORBIDDEN);
        }
        handler.handleDel(detail);
    }

    /**
     * 校验记账的数据是否合规
     */
    private void beforeHandle(Long id, Integer userId, Integer targetAccountId, Integer recordBookId) {
        // 校验账户和账单是否属于该用户
        RecordAccountDO recordAccountDO = recordAccountService.getById(targetAccountId);
        if (recordAccountDO == null || !recordAccountDO.getUserId().equals(userId)) {
            throw new BusinessException(CodeMsg.OPERATE_RECORD_ACCOUNT_FORBIDDEN);
        }
        RecordBookDO recordBookDO = recordBookService.getById(recordBookId);
        if (recordBookDO == null || !recordBookDO.getUserId().equals(userId)) {
            throw new BusinessException(CodeMsg.OPERATE_RECORD_BOOK_FORBIDDEN);
        }
        // 如果有id，校验记录是否存在
        if (id != null && recordDetailService.getById(id) == null){
            throw new BusinessException(CodeMsg.NOT_FIND_DATA);
        }

    }
}
