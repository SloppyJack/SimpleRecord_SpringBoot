package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.constant.CommonConstants;
import cn.jackbin.SimpleRecord.constant.RecordConstant;
import cn.jackbin.SimpleRecord.dto.RecordBookAnalysisDTO;
import cn.jackbin.SimpleRecord.dto.RecordDetailBookSumDTO;
import cn.jackbin.SimpleRecord.entity.DictDO;
import cn.jackbin.SimpleRecord.entity.DictItemDO;
import cn.jackbin.SimpleRecord.entity.RecordBookDO;
import cn.jackbin.SimpleRecord.mapper.RecordBookMapper;
import cn.jackbin.SimpleRecord.service.DictItemService;
import cn.jackbin.SimpleRecord.service.DictService;
import cn.jackbin.SimpleRecord.service.RecordBookService;
import cn.jackbin.SimpleRecord.service.RecordDetailService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.service.impl
 * @date: 2021/7/13 22:04
 **/
@Service
public class RecordBookServiceImpl extends ServiceImpl<RecordBookMapper, RecordBookDO> implements RecordBookService {
    @Autowired
    private RecordBookMapper recordBookMapper;
    @Autowired
    private DictItemService dictItemService;
    @Autowired
    private DictService dictService;
    @Autowired
    private RecordDetailService recordDetailService;

    @Override
    public void getByPage(Integer userId, PageBO<RecordBookAnalysisDTO> pageBO) {
        IPage<RecordBookDO> page = new Page<>(pageBO.getPageNo(), pageBO.getPageSize());
        QueryWrapper<RecordBookDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByAsc("order_no");
        recordBookMapper.selectPage(page, queryWrapper);
        // bean转换
        List<RecordBookAnalysisDTO> retList = new ArrayList<>();
        page.getRecords().forEach(n -> {
            RecordBookAnalysisDTO dto = new RecordBookAnalysisDTO();
            BeanUtils.copyProperties(n, dto);
            retList.add(dto);
        });
        // 如果又记录，才尝试获取账本的支出和收入
        if (retList.size() > 0){
            // 获取dictDO
            DictDO dictDO = dictService.getByCode(RecordConstant.RECORD_TYPE);
            DictItemDO expendDictItem = dictItemService.getByValue(dictDO.getId().intValue(), RecordConstant.EXPEND_RECORD_TYPE);
            DictItemDO incomeDictItem = dictItemService.getByValue(dictDO.getId().intValue(), RecordConstant.INCOME_RECORD_TYPE);
            // 拼接账本总计
            List<Integer> recordBookIds = page.getRecords().stream().map(n -> n.getId().intValue()).collect(Collectors.toList());
            List<RecordDetailBookSumDTO> expendSumList = recordDetailService.getSumByRecordBookIds(expendDictItem.getId().intValue(), recordBookIds);
            List<RecordDetailBookSumDTO> incomeSumList = recordDetailService.getSumByRecordBookIds(incomeDictItem.getId().intValue(), recordBookIds);
            // 遍历retList并赋值
            retList.forEach(n -> {
                Iterator<RecordDetailBookSumDTO> eIterator = expendSumList.iterator();
                while (eIterator.hasNext()){
                    RecordDetailBookSumDTO temp = eIterator.next();
                    if (n.getId().intValue() == temp.getRecordBookId()){
                        n.setExpendTotal(temp.getAmountTotal());
                        // 赋值后移除该元素
                        eIterator.remove();
                        break;
                    }
                }
                Iterator<RecordDetailBookSumDTO> iIterator = incomeSumList.iterator();
                while (iIterator.hasNext()){
                    RecordDetailBookSumDTO temp = iIterator.next();
                    if (n.getId().intValue() == temp.getRecordBookId()){
                        n.setIncomeTotal(temp.getAmountTotal());
                        // 赋值后移除该元素
                        iIterator.remove();
                        break;
                    }
                }
            });
        }
        pageBO.setTotal((int) page.getTotal());
        pageBO.setList(retList);
    }

    @Override
    public void add(Integer userId, String name, String remark, Integer orderNo) {
        RecordBookDO recordBookDO = new RecordBookDO();
        recordBookDO.setUserId(userId);
        recordBookDO.setName(name);
        recordBookDO.setRemark(remark);
        recordBookDO.setOrderNo(orderNo);
        recordBookDO.setStatus(CommonConstants.STATUS_NORMAL);
        // 新增的账本为非用户默认
        recordBookDO.setIsUserDefault(RecordConstant.NOT_USER_DEFAULT);
        recordBookMapper.insert(recordBookDO);
    }

    @Override
    public void getByName(String name) {
        QueryWrapper<RecordBookDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        recordBookMapper.selectOne(queryWrapper);
    }

    @Override
    public RecordBookDO getDefaultBook(Integer userId) {
        QueryWrapper<RecordBookDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_user_default", RecordConstant.USER_DEFAULT);
        queryWrapper.eq("user_id", userId);
        return recordBookMapper.selectOne(queryWrapper);
    }

    @Override
    public void edit(Long id, Integer userId, String name, String remark, Integer orderNo, Integer isUserDefault) {
        RecordBookDO recordBookDO = RecordBookDO.builder().id(id).userId(userId).
                name(name).remark(remark).orderNo(orderNo).isUserDefault(isUserDefault).build();
        recordBookMapper.updateById(recordBookDO);
    }

    @Transactional
    @Override
    public void updateDefault(Long defaultId, Long sourceId, Integer userId, String name, String remark, Integer orderNo, Integer isUserDefault) {
        // 取消原有默认账单
        RecordBookDO defaultBookDO = RecordBookDO.builder().id(defaultId).isUserDefault(RecordConstant.NOT_USER_DEFAULT).build();
        recordBookMapper.updateById(defaultBookDO);
        // 更新目标账单
        RecordBookDO recordBookDO = RecordBookDO.builder().id(sourceId).userId(userId).
                name(name).remark(remark).orderNo(orderNo).isUserDefault(isUserDefault).build();
        recordBookMapper.updateById(recordBookDO);
    }

    @Override
    public List<RecordBookDO> getList(Integer userId) {
        QueryWrapper<RecordBookDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByAsc("order_no");
        return recordBookMapper.selectList(queryWrapper);
    }
}
