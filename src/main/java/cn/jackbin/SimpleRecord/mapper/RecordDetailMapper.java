package cn.jackbin.SimpleRecord.mapper;

import cn.jackbin.SimpleRecord.dto.MonthRecordAnalysisDTO;
import cn.jackbin.SimpleRecord.dto.RecordDetailBookSumDTO;
import cn.jackbin.SimpleRecord.dto.SpendCategoryTotalDTO;
import cn.jackbin.SimpleRecord.entity.RecordDetailDO;
import cn.jackbin.SimpleRecord.dto.RecordDetailDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.mapper
 * @date: 2020/9/16 22:30
 **/
@Repository
public interface RecordDetailMapper extends BaseMapper<RecordDetailDO> {

    /**
     * 根据月份查询总额
     * @param userId 用户Id
     * @param recordType 记录类型Id
     * @param date 时间
     * @return List
     */
    Double queryTotalByMonth(Integer userId, Integer recordType, Date date);

    /**
     * 获取花费类别的消费总额
     * @param userId 用户Id
     * @param recordTypeCode 记账类型Code
     * @param date 时间
     * @param begin 索引
     * @param end 索引
     * @return
     */
    List<SpendCategoryTotalDTO> querySpendSpendCategoryTotalByMonth(Integer userId, Integer recordType, Date date, int begin, int end);

    List<SpendCategoryTotalDTO> querySpendSpendCategoryTotalByYear(Integer userId, Integer recordType, Date date);

    /**
     * 查询用户某个月内的记账记录
     */
    IPage<RecordDetailDTO> queryByMonthAndBook(Page<?> page, Integer recordBookId, Integer userId, Date date, Date occurTime, String keyWord);

    IPage<RecordDetailDTO> queryByMonthAndAccount(Page<?> page, Integer recordAccountId, Integer userId, Date date, Date occurTime, String keyWord);

    /**
     * 查询报销记录
     */
    IPage<RecordDetailDTO> queryRecoverableList(Page<?> page, Integer userId, Integer recoverableStatus);

    List<MonthRecordAnalysisDTO> queryByInterval(Integer userId, Integer recordType, Date beginDate, Date endDate);

    /**
     * 获取账本的总计
     */
    List<RecordDetailBookSumDTO> querySumByRecordBookIds(Integer recordTypeId, List<Integer> recordBookIds);
}
