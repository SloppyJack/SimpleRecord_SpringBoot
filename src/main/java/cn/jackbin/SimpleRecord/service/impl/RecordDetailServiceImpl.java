package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.common.LocalUser;
import cn.jackbin.SimpleRecord.constant.RecordConstant;
import cn.jackbin.SimpleRecord.dto.SpendCategoryTotalDTO;
import cn.jackbin.SimpleRecord.dto.RecordDTO;
import cn.jackbin.SimpleRecord.entity.RecordDetailDO;
import cn.jackbin.SimpleRecord.entity.UserDO;
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
    public boolean createRecord(RecordDTO recordDTO) {
        RecordDetailDO recordDO = new RecordDetailDO();
        // 设置记账人
        UserDO userDO = LocalUser.getLocalUser();
        recordDO.setUserId(userDO.getId().intValue());
        recordDO.setSpendCategoryId(recordDTO.getSpendCategoryId().intValue());
        recordDO.setAmount(recordDTO.getAmount());
        recordDO.setOccurTime(recordDTO.getOccurTime());
        recordDO.setRemarks(recordDTO.getRemarks());
        return recordDetailMapper.insert(recordDO) > 0;
    }

    @Override
    public List<RecordDetailDO> getRecordsByLocalUser() {
        UserDO userDO = LocalUser.getLocalUser();
        QueryWrapper<RecordDetailDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userDO.getId());
        return recordDetailMapper.selectList(queryWrapper);
    }

    @Override
    public List<RecordDetailDO> getRecordsByLocalUserByPage(int pageIndex, int pageSize) {
        UserDO userDO = LocalUser.getLocalUser();
        IPage<RecordDetailDO> page = new Page<>(pageIndex, pageSize);//参数一是当前页，参数二是每页个数
        QueryWrapper<RecordDetailDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userDO.getId());
        page = recordDetailMapper.selectPage(page, queryWrapper);
        return page.getRecords();
    }

    @Override
    public RecordDetailDO getById(Long id) {
        return recordDetailMapper.selectById(id);
    }

    @Override
    public boolean updateRecord(RecordDetailDO recordDetailDO, RecordDTO dto) {
        if (dto.getSpendCategoryId() != null) {
            dto.setSpendCategoryId(dto.getSpendCategoryId());
        }
        recordDetailDO.setOccurTime(dto.getOccurTime());
        recordDetailDO.setAmount(dto.getAmount());
        recordDetailDO.setRemarks(dto.getRemarks());
        return recordDetailMapper.updateById(recordDetailDO) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        return recordDetailMapper.deleteById(id) > 0;
    }

    @Override
    public List<Double> getSpendTotalByMonth(Long userId, Date date) {
        List<Double> list = new ArrayList<>();
        list.add(recordDetailMapper.querySpendTotalByMonth(userId, RecordConstant.EXPEND_RECORD_TYPE, date));
        list.add(recordDetailMapper.querySpendTotalByMonth(userId, RecordConstant.INCOME_RECORD_TYPE, date));
        return list;
    }

    @Override
    public List<SpendCategoryTotalDTO> getSpendTotalBySpendCategory(Long userId, String recordTypeCode, Date date, int begin, int end) {
        return recordDetailMapper.querySpendTotalBySpendCategory(userId, recordTypeCode, date, begin, end);
    }

    @Override
    public List<RecordDetailDTO> getListByMonth(Long userId, String recordTypeCode, Date date, int pageIndex, int pageSize) {
        return recordDetailMapper.queryByMonth(userId, recordTypeCode, date, pageIndex * pageSize, pageSize);
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
