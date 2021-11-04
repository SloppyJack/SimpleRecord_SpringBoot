package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.constant.CommonConstants;
import cn.jackbin.SimpleRecord.constant.RecordConstant;
import cn.jackbin.SimpleRecord.dto.SpendCategoryTotalDTO;
import cn.jackbin.SimpleRecord.entity.RecordDetailDO;
import cn.jackbin.SimpleRecord.mapper.RecordDetailMapper;
import cn.jackbin.SimpleRecord.service.RecordDetailService;
import cn.jackbin.SimpleRecord.dto.RecordDetailDTO;
import cn.jackbin.SimpleRecord.utils.DateUtil;
import cn.jackbin.SimpleRecord.bo.MonthRecordBO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void add(Integer userId, Integer recordAccountId, Integer recordBookId, Integer recordTypeId, String recordCategory, Double amount,
                    Date occurTime, String tag, String remark, Boolean isRecoverable) {
        RecordDetailDO recordDetailDO = new RecordDetailDO();
        recordDetailDO.setUserId(userId);
        recordDetailDO.setRecordAccountId(recordAccountId);
        recordDetailDO.setRecordBookId(recordBookId);
        recordDetailDO.setRecordType(recordTypeId);
        recordDetailDO.setRecordCategory(recordCategory);
        recordDetailDO.setOccurTime(occurTime);
        recordDetailDO.setAmount(amount);
        recordDetailDO.setTag(tag);
        recordDetailDO.setRemark(remark);
        recordDetailDO.setIsRecoverable(isRecoverable);
        recordDetailMapper.insert(recordDetailDO);
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
    public RecordDetailDO getById(Long id) {
        return recordDetailMapper.selectById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return recordDetailMapper.deleteById(id) > 0;
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
    public PageBO<RecordDetailDTO> getListByMonth(Long userId, String recordTypeCode, Date date, int pageIndex, int pageSize) {
        IPage<RecordDetailDTO> dto = recordDetailMapper.queryByMonth(new Page<>(pageIndex * pageSize, pageSize), userId, recordTypeCode, date);
        return new PageBO<>(dto.getRecords(), pageIndex, pageSize, (int) dto.getTotal());
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
