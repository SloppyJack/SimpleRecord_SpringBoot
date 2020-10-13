package cn.jackbin.SimpleRecord.service;

import cn.jackbin.SimpleRecord.dto.CreateOrUpdateRecordDTO;
import cn.jackbin.SimpleRecord.entity.RecordDetailDO;
import cn.jackbin.SimpleRecord.vo.SpendTotalByCategoryVO;
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
    boolean createRecord(CreateOrUpdateRecordDTO recordDTO);

    /**
     * 获取当前登录用户的记账记录
     */
    List<RecordDetailDO> getRecordsByLocalUser();

    /**
     * 分页获取当前登录用户的记账记录
     */
    List<RecordDetailDO> getRecordsByLocalUserByPage(int pageIndex, int pageSize);

    /**
     * 通过Id获取记账记录
     */
    RecordDetailDO getById(Long id);

    /**
     * 更新
     */
    boolean updateRecord(RecordDetailDO recordDetailDO,CreateOrUpdateRecordDTO dto);

    /**
     * 通过Id删除某条记账记录
     */
    boolean deleteById(Long id);

    /**
     * 获取支出
     */
    List<Double> getSpendTotalByMonth(Long userId, Date date);

    /**
     * 获取前三消费额
     */
    List<SpendTotalByCategoryVO> getSpendTotalBySpendCategory(Long userId, String recordTypeCode, Date date, int begin, int end);
}
