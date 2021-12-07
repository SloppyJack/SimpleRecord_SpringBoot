package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.common.anotations.HandleDict;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.constant.CommonConstants;
import cn.jackbin.SimpleRecord.constant.RecordConstant;
import cn.jackbin.SimpleRecord.dto.SpendCategoryTotalDTO;
import cn.jackbin.SimpleRecord.entity.RecordDetailDO;
import cn.jackbin.SimpleRecord.exception.BusinessException;
import cn.jackbin.SimpleRecord.mapper.RecordDetailMapper;
import cn.jackbin.SimpleRecord.service.RecordDetailService;
import cn.jackbin.SimpleRecord.dto.RecordDetailDTO;
import cn.jackbin.SimpleRecord.utils.DateUtil;
import cn.jackbin.SimpleRecord.bo.MonthRecordBO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public int add(Integer userId, Integer recordAccountId, Integer recordBookId, Integer recordTypeId, String recordCategory, Double amount,
                    Date occurTime, String tag, String remark, Boolean isRecoverable) {
        return add(userId, recordAccountId, null, null, recordBookId, null, recordTypeId, recordCategory,
                amount, occurTime, tag, remark, isRecoverable);
    }

    @Override
    public int add(Integer userId, Integer recordAccountId, Integer sourceAccountId, Integer targetAccountId, Integer recordBookId,
                   Integer relationRecordId, Integer recordTypeId, String recordCategory, Double amount, Date occurTime,
                   String tag, String remark, Boolean isRecoverable) {
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
        recordDetailDO.setIsRecoverable(isRecoverable);
        recordDetailDO.setStatus(CommonConstants.STATUS_NORMAL);
        if (recordDetailMapper.insert(recordDetailDO) < 1){
            throw new BusinessException(CodeMsg.ADD_DATA_ERROR);
        }
        return recordDetailDO.getId().intValue();
    }

    @Override
    public void update(Long id, Integer recordAccountId, Integer recordBookId, Integer recordTypeId, String recordCategory, Double amount, Date occurTime, String tag, String remark, Boolean isRecoverable) {
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
        recordDetailDO.setIsRecoverable(isRecoverable);
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
    public List<RecordDetailDO> getRecordsByUserId(Long userId) {
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
    public List<Double> getSpendTotalByMonth(Long userId, Date date) {
        List<Double> list = new ArrayList<>();
        Double expendTotal = recordDetailMapper.querySpendTotalByMonth(userId, RecordConstant.EXPEND_RECORD_TYPE, date);
        Double incomeTotal = recordDetailMapper.querySpendTotalByMonth(userId, RecordConstant.INCOME_RECORD_TYPE, date);
        list.add(Objects.requireNonNullElse(expendTotal, 0.0));
        list.add(Objects.requireNonNullElse(incomeTotal, 0.0));
        return list;
    }

    @Override
    public List<SpendCategoryTotalDTO> getSpendTotalBySpendCategory(Long userId, String recordTypeCode, Date date, int begin, int end) {
        return recordDetailMapper.querySpendSpendCategoryTotalByMonth(userId, recordTypeCode, date, begin, end);
    }

    @Override
    public List<SpendCategoryTotalDTO> getSpendSpendCategoryTotalByYear(Long userId, String recordTypeCode, Date date) {
        return recordDetailMapper.querySpendSpendCategoryTotalByYear(userId, recordTypeCode, date);
    }

    @Override
    public void getListByMonth(Long userId, Date date, Date occurTime, String keyWord, PageBO<RecordDetailDTO> pageBO) {
        Page<RecordDetailDTO> page = new Page<>(pageBO.beginPosition(), pageBO.getPageSize());
        recordDetailMapper.queryByMonth(page, userId, date, occurTime, keyWord);
        pageBO.setTotal((int) page.getTotal());
        pageBO.setList(page.getRecords());
    }

    @Override
    public List<MonthRecordBO> getLatestSixMonthList(Long userId, String recordTypeCode, Date beginDate, Date endDate) {
        List<MonthRecordBO> monthRecords = new ArrayList<>();
        List<RecordDetailDTO> recordDetailDTOList = recordDetailMapper.queryByInterval(userId, recordTypeCode, beginDate, endDate);
        // 将时间分段
        List<Long> intervalDate = DateUtil.getIntervalTimeByMonth(beginDate, endDate);
        int beginIndex = 0; // 开始标记
        int endIndex = 0;   // 结束标记
        for (int i=0; i<intervalDate.size() -1; i++) {
            boolean flag = false;
            // 找到同一个月份的并打上标记
            for (RecordDetailDTO temp : recordDetailDTOList) {
                if (temp.getOccurTime().getTime() >= intervalDate.get(i) &&temp.getOccurTime().getTime() < intervalDate.get(i+1)) {
                    flag = true;
                    endIndex ++;
                }
            }
            MonthRecordBO monthRecord = new MonthRecordBO();
            monthRecord.setOccurMonth(new Date(intervalDate.get(i)));
            // 如果需要处理数据
            if (flag) {
                for (int j = beginIndex; j<endIndex; j++) {
                    monthRecord.setTotal(monthRecord.getTotal() + recordDetailDTOList.get(j).getAmount());
                }
            }
            monthRecords.add(monthRecord);
        }
        return monthRecords;
    }
}
