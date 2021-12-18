package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.constant.CommonConstants;
import cn.jackbin.SimpleRecord.dto.RecordAccountAnalysisDTO;
import cn.jackbin.SimpleRecord.entity.RecordAccountDO;
import cn.jackbin.SimpleRecord.mapper.RecordAccountMapper;
import cn.jackbin.SimpleRecord.service.RecordAccountService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.service.impl
 * @date: 2021/7/13 22:04
 **/
@Service
public class RecordAccountServiceImpl extends ServiceImpl<RecordAccountMapper, RecordAccountDO> implements RecordAccountService {
    @Autowired
    private RecordAccountMapper recordAccountMapper;

    @Override
    public List<RecordAccountDO> getListByUserId(Integer userId) {
        QueryWrapper<RecordAccountDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByAsc("order_no");
        return recordAccountMapper.selectList(queryWrapper);
    }

    @Override
    public void add(Integer userId, Integer type, String name, Integer inNetAssets, Integer orderNo) {
        RecordAccountDO recordAccountDO = new RecordAccountDO();
        recordAccountDO.setUserId(userId);
        recordAccountDO.setType(type);
        recordAccountDO.setName(name);
        recordAccountDO.setInNetAssets(inNetAssets);
        recordAccountDO.setStatus(CommonConstants.STATUS_NORMAL);
        recordAccountDO.setOrderNo(orderNo);
        recordAccountMapper.insert(recordAccountDO);
    }

    @Override
    public void update(Long id, Integer type, String name, Integer inNetAssets, Integer orderNo) {
        RecordAccountDO recordAccountDO = new RecordAccountDO();
        recordAccountDO.setId(id);
        recordAccountDO.setType(type);
        recordAccountDO.setName(name);
        recordAccountDO.setInNetAssets(inNetAssets);
        recordAccountDO.setOrderNo(orderNo);
        recordAccountMapper.updateById(recordAccountDO);
    }

    @Override
    public List<RecordAccountAnalysisDTO> analysisAccounts(Integer userId) {
        return recordAccountMapper.queryInAndOutTotal(userId);
    }
}
