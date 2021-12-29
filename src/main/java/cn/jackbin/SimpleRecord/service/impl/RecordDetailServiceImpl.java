package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.constant.CommonConstants;
import cn.jackbin.SimpleRecord.constant.RecordConstant;
import cn.jackbin.SimpleRecord.dto.RecordDetailBookSumDTO;
import cn.jackbin.SimpleRecord.dto.SpendCategoryTotalDTO;
import cn.jackbin.SimpleRecord.entity.DictDO;
import cn.jackbin.SimpleRecord.entity.DictItemDO;
import cn.jackbin.SimpleRecord.entity.RecordDetailDO;
import cn.jackbin.SimpleRecord.exception.BusinessException;
import cn.jackbin.SimpleRecord.mapper.RecordDetailMapper;
import cn.jackbin.SimpleRecord.service.DictItemService;
import cn.jackbin.SimpleRecord.service.DictService;
import cn.jackbin.SimpleRecord.service.RecordDetailService;
import cn.jackbin.SimpleRecord.dto.RecordDetailDTO;
import cn.jackbin.SimpleRecord.utils.DateUtil;
import cn.jackbin.SimpleRecord.dto.MonthRecordAnalysisDTO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jackbin
 * @since 2020-07-21
 */
@Service
public class RecordDetailServiceImpl extends ServiceImpl<RecordDetailMapper, RecordDetailDO> implements RecordDetailService {
    @Autowired
    private RecordDetailMapper recordDetailMapper;
    @Autowired
    private DictItemService dictItemService;
    @Autowired
    private DictService dictService;

    @Override
    public int add(Integer userId, Integer recordAccountId, Integer recordBookId, Integer recordTypeId, String recordCategory, Double amount,
                    Date occurTime, String tag, String remark, Integer recoverableStatus) {
        return add(userId, recordAccountId, null, null, recordBookId, null, recordTypeId, recordCategory,
                amount, occurTime, tag, remark, recoverableStatus);
    }

    @Override
    public int add(Integer userId, Integer recordAccountId, Integer sourceAccountId, Integer targetAccountId, Integer recordBookId,
                   Integer relationRecordId, Integer recordTypeId, String recordCategory, Double amount, Date occurTime,
                   String tag, String remark, Integer recoverableStatus) {
        RecordDetailDO recordDetailDO = new RecordDetailDO();
        recordDetailDO.setUserId(userId);
        recordDetailDO.setRecordAccountId(recordAccountId);
        recordDetailDO.setSourceAccountId(sourceAccountId);
        recordDetailDO.setTargetAccountId(targetAccountId);
        recordDetailDO.setRecordBookId(recordBookId);
        recordDetailDO.setRelationRecordId(relationRecordId);
        recordDetailDO.setRecordType(recordTypeId);
        recordDetailDO.setRecordCategory(recordCategory);
        recordDetailDO.setOccurTime(occurTime);
        recordDetailDO.setAmount(amount);
        recordDetailDO.setTag(tag);
        recordDetailDO.setRemark(remark);
        recordDetailDO.setRecoverableStatus(recoverableStatus);
        recordDetailDO.setStatus(CommonConstants.STATUS_NORMAL);
        if (recordDetailMapper.insert(recordDetailDO) < 1){
            throw new BusinessException(CodeMsg.ADD_DATA_ERROR);
        }
        return recordDetailDO.getId().intValue();
    }

    @Override
    public void update(Long id, Integer recordAccountId, Integer recordBookId, Integer recordTypeId, String recordCategory, Double amount, Date occurTime, String tag, String remark, Integer recoverableStatus) {
        RecordDetailDO recordDetailDO = new RecordDetailDO();
        recordDetailDO.setId(id);
        recordDetailDO.setRecordAccountId(recordAccountId);
        recordDetailDO.setRecordBookId(recordBookId);
        recordDetailDO.setRecordType(recordTypeId);
        recordDetailDO.setRecordCategory(recordCategory);
        recordDetailDO.setOccurTime(occurTime);
        recordDetailDO.setAmount(amount);
        recordDetailDO.setTag(tag);
        recordDetailDO.setRemark(remark);
        recordDetailDO.setRecoverableStatus(recoverableStatus);
        recordDetailMapper.updateById(recordDetailDO);
    }

    @Override
    public void update(Long id, Integer recordBookId, Double amount, Date occurTime, String tag, String remark) {
        RecordDetailDO recordDetailDO = new RecordDetailDO();
        recordDetailDO.setId(id);
        recordDetailDO.setRecordBookId(recordBookId);
        recordDetailDO.setOccurTime(occurTime);
        recordDetailDO.setAmount(amount);
        recordDetailDO.setTag(tag);
        recordDetailDO.setRemark(remark);
        recordDetailMapper.updateById(recordDetailDO);
    }

    @Override
    public RecordDetailDO getByRId(Integer rid) {
        QueryWrapper<RecordDetailDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("relation_record_id", rid);
        return recordDetailMapper.selectOne(queryWrapper);
    }

    @Override
    public void updateRId(Long id, Integer rid) {
        RecordDetailDO recordDetailDO = new RecordDetailDO();
        recordDetailDO.setId(id);
        recordDetailDO.setRelationRecordId(rid);
        recordDetailMapper.updateById(recordDetailDO);
    }

    @Override
    public void removeByRId(Long rid) {
        QueryWrapper<RecordDetailDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("relation_record_id", rid);
        recordDetailMapper.delete(queryWrapper);
    }

    @Override
    public List<RecordDetailDO> getRecordsByUserId(Integer userId) {
        QueryWrapper<RecordDetailDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        return recordDetailMapper.selectList(queryWrapper);
    }

    @Override
    public List<RecordDetailDO> getRecordsByByPage(Long userId, int pageIndex, int pageSize) {
        IPage<RecordDetailDO> page = new Page<>(pageIndex, pageSize);//参数一是当前页，参数二是每页个数
        QueryWrapper<RecordDetailDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        page = recordDetailMapper.selectPage(page, queryWrapper);
        return page.getRecords();
    }

    @Override
    public List<Double> getSpendTotalByMonth(Integer userId, Date date) {
        List<Double> list = new ArrayList<>();
        DictDO dictDO = dictService.getByCode(RecordConstant.RECORD_TYPE);
        DictItemDO expendDictItemDO = dictItemService.getByValue(dictDO.getId().intValue(), RecordConstant.EXPEND_RECORD_TYPE);
        DictItemDO incomeDictItemDO = dictItemService.getByValue(dictDO.getId().intValue(), RecordConstant.INCOME_RECORD_TYPE);
        Double expendTotal = recordDetailMapper.queryTotalByMonth(userId, expendDictItemDO.getId().intValue(), date);
        Double incomeTotal = recordDetailMapper.queryTotalByMonth(userId, incomeDictItemDO.getId().intValue(), date);
        list.add(Objects.requireNonNullElse(expendTotal, 0.0));
        list.add(Objects.requireNonNullElse(incomeTotal, 0.0));
        return list;
    }

    @Override
    public List<SpendCategoryTotalDTO> getSpendTotalBySpendCategory(Integer userId, String recordTypeCode, Date date, int begin, int end) {
        DictDO dictDO = dictService.getByCode(RecordConstant.RECORD_TYPE);
        DictItemDO dictItemDO = dictItemService.getByValue(dictDO.getId().intValue(), recordTypeCode);
        return recordDetailMapper.querySpendSpendCategoryTotalByMonth(userId, dictItemDO.getId().intValue(), date, begin, end);
    }

    @Override
    public List<SpendCategoryTotalDTO> getSpendSpendCategoryTotalByYear(Integer userId, String recordTypeCode, Date date) {
        DictDO dictDO = dictService.getByCode(RecordConstant.RECORD_TYPE);
        DictItemDO dictItemDO = dictItemService.getByValue(dictDO.getId().intValue(), recordTypeCode);
        return recordDetailMapper.querySpendSpendCategoryTotalByYear(userId, dictItemDO.getId().intValue(), date);
    }

    @Override
    public void getMonthBookRecords(Integer recordBookId, Integer userId, Date date, Date occurTime, String keyWord, PageBO<RecordDetailDTO> pageBO) {
        Page<RecordDetailDTO> page = new Page<>(pageBO.getPageNo(), pageBO.getPageSize());
        recordDetailMapper.queryByMonthAndBook(page, recordBookId, userId, date, occurTime, keyWord);
        pageBO.setTotal((int) page.getTotal());
        pageBO.setList(page.getRecords());
    }

    @Override
    public void getMonthAccountRecords(Integer recordAccountId, Integer userId, Date date, Date occurTime, String keyWord, PageBO<RecordDetailDTO> pageBO) {
        Page<RecordDetailDTO> page = new Page<>(pageBO.getPageNo(), pageBO.getPageSize());
        recordDetailMapper.queryByMonthAndAccount(page, recordAccountId, userId, date, occurTime, keyWord);
        pageBO.setTotal((int) page.getTotal());
        pageBO.setList(page.getRecords());
    }

    @Override
    public void getRecoverableList(Integer userId, Integer recoverableStatus, PageBO<RecordDetailDTO> pageBO) {
        Page<RecordDetailDTO> page = new Page<>(pageBO.getPageNo(), pageBO.getPageSize());
        recordDetailMapper.queryRecoverableList(page, userId, recoverableStatus);
        pageBO.setTotal((int) page.getTotal());
        pageBO.setList(page.getRecords());
    }

    @Override
    public List<MonthRecordAnalysisDTO> getLatestSixMonthList(Integer userId, String recordTypeCode, Date beginDate, Date endDate) {
        DictDO dictDO = dictService.getByCode(RecordConstant.RECORD_TYPE);
        DictItemDO dictItemDO = dictItemService.getByValue(dictDO.getId().intValue(), recordTypeCode);
        List<MonthRecordAnalysisDTO> ret = new ArrayList<>();
        List<MonthRecordAnalysisDTO> recordAnalysisDTOS = recordDetailMapper.queryByInterval(userId, dictItemDO.getId().intValue(), beginDate, endDate);
        // 补充缺失的月份
        List<Long> intervalDate = DateUtil.getIntervalTimeByMonth(beginDate, endDate);
        int beginIndex = 0; // 开始标记
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        try {
            out: for (int i=0; i<intervalDate.size() -1; i++) {
                Date tempDate = new Date(intervalDate.get(i));
                for (int j=beginIndex; j<recordAnalysisDTOS.size(); j++){
                    MonthRecordAnalysisDTO temp = recordAnalysisDTOS.get(j);
                    if (temp == null)
                        continue ;
                    if (tempDate.equals(sdf.parse(temp.getOccurMonth()))){
                        ret.add(temp);
                        beginIndex++;
                        continue out;   // 进入下一次循环
                    }
                }
                MonthRecordAnalysisDTO monthRecord = new MonthRecordAnalysisDTO();
                monthRecord.setOccurMonth(sdf.format(tempDate));
                ret.add(monthRecord);
            }
        } catch (ParseException e){
            throw new BusinessException(CodeMsg.FAILED);
        }

        return ret;
    }

    @Override
    public List<RecordDetailBookSumDTO> getSumByRecordBookIds(Integer recordTypeId, List<Integer> recordBookIds) {
        return recordDetailMapper.querySumByRecordBookIds(recordTypeId, recordBookIds);
    }
}
