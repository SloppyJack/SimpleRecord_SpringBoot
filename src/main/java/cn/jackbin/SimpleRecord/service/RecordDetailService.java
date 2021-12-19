package cn.jackbin.SimpleRecord.service;

import cn.jackbin.SimpleRecord.bo.MonthRecordBO;
import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.dto.RecordDetailDTO;
import cn.jackbin.SimpleRecord.dto.RecordDetailBookSumDTO;
import cn.jackbin.SimpleRecord.dto.SpendCategoryTotalDTO;
import cn.jackbin.SimpleRecord.entity.RecordDetailDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jackbin
 * @since 2020-07-21
 */
public interface RecordDetailService extends IService<RecordDetailDO> {

    int add(Integer userId, Integer recordAccountId, Integer recordBookId, Integer recordTypeId, String recordCategory, Double amount,
             Date occurTime, String tag, String remark, Integer recoverableStatus);

    int add(Integer userId, Integer recordAccountId, Integer sourceAccountId, Integer targetAccountId, Integer recordBookId, Integer relationRecordId, Integer recordTypeId,
             String recordCategory, Double amount, Date occurTime, String tag, String remark, Integer recoverableStatus);

    void update(Long id, Integer recordAccountId, Integer recordBookId, Integer recordTypeId, String recordCategory, Double amount,
            Date occurTime, String tag, String remark, Integer recoverableStatus
    );

    void update(Long id, Integer recordBookId, Double amount, Date occurTime, String tag, String remark);

    /**
     * 找到关联记录
     * @param rid 关联id
     * @return
     */
    RecordDetailDO getByRId(Integer rid);
    /**
     * 更新关联id
     * @param id
     * @param rid
     */
    void updateRId(Long id, Integer rid);

    void removeByRId(Long rid);

    /**
     * 获取当前登录用户的记账记录
     */
    List<RecordDetailDO> getRecordsByUserId(Long userId);

    /**
     * 分页获取当前登录用户的记账记录
     */
    List<RecordDetailDO> getRecordsByByPage(Long userId, int pageIndex, int pageSize);

    /**
     * 获取支出
     */
    List<Double> getSpendTotalByMonth(Long userId, Date date);

    /**
     * 获取某个月前三消费额
     */
    List<SpendCategoryTotalDTO> getSpendTotalBySpendCategory(Long userId, String recordTypeCode, Date date, int begin, int end);

    List<SpendCategoryTotalDTO> getSpendSpendCategoryTotalByYear(Long userId, String recordTypeCode, Date date);

    /**
     * 查询用户某个月内的记账记录
     */
    void getListByMonth(Long userId, Date date, Date occurTime, String keyWord, PageBO<RecordDetailDTO> pageBO);

    void getRecoverableList(Long userId, Integer recoverableStatus, PageBO<RecordDetailDTO> pageBO);

    /**
     * 查询用户六个月内的记账记录
     */
    List<MonthRecordBO> getLatestSixMonthList(Long userId, String recordTypeCode, Date beginDate, Date endDate);

    List<RecordDetailBookSumDTO> getSumByRecordBookIds(Integer recordTypeId, List<Integer> recordBookIds);
}
