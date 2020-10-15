package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.common.LocalUser;
import cn.jackbin.SimpleRecord.constant.RecordConstant;
import cn.jackbin.SimpleRecord.dto.SpendCategoryTotalDTO;
import cn.jackbin.SimpleRecord.dto.RecordDTO;
import cn.jackbin.SimpleRecord.entity.RecordDetailDO;
import cn.jackbin.SimpleRecord.entity.RecordTypeDO;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.mapper.RecordDetailMapper;
import cn.jackbin.SimpleRecord.mapper.RecordTypeMapper;
import cn.jackbin.SimpleRecord.service.RecordDetailService;
import cn.jackbin.SimpleRecord.dto.RecordDetailDTO;
import cn.jackbin.SimpleRecord.service.RecordTypeService;
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
}
