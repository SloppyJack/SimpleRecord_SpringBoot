package cn.jackbin.SimpleRecord.service;

import cn.jackbin.SimpleRecord.bo.MonthRecordBO;
import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.dto.SpendCategoryTotalDTO;
import cn.jackbin.SimpleRecord.dto.RecordDTO;
import cn.jackbin.SimpleRecord.entity.RecordDetailDO;
import cn.jackbin.SimpleRecord.dto.RecordDetailDTO;
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

    /**
     * 添加一条记账
     */
    boolean createRecord(RecordDTO recordDTO, Long userId);

    /**
     * 获取当前登录用户的记账记录
     */
    List<RecordDetailDO> getRecordsByUserId(Long userId);

    /**
     * 分页获取当前登录用户的记账记录
     */
    List<RecordDetailDO> getRecordsByByPage(Long userId, int pageIndex, int pageSize);

    /**
     * 通过Id获取记账记录
     */
    RecordDetailDO getById(Long id);

    /**
     * 更新
     */
    boolean updateRecord(RecordDetailDO recordDetailDO, RecordDTO dto);

    /**
     * 通过Id删除某条记账记录
     */
    boolean deleteById(Long id);

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
    PageBO<RecordDetailDTO> getListByMonth(Long userId, String recordTypeCode, Date date, int pageIndex, int pageSize);

    /**
     * 查询用户六个月内的记账记录
     */
    List<MonthRecordBO> getLatestSixMonthList(Long userId, String recordTypeCode, Date beginDate, Date endDate);
}
