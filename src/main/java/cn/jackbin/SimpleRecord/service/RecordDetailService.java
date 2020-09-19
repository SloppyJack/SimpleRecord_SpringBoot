package cn.jackbin.SimpleRecord.service;

import cn.jackbin.SimpleRecord.dto.CreateOrUpdateRecordDTO;
import cn.jackbin.SimpleRecord.dto.PageDTO;
import cn.jackbin.SimpleRecord.entity.RecordDetailDO;
import com.baomidou.mybatisplus.extension.service.IService;

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
     *@description: 获取当前登录用户的记账记录
     */
    List<RecordDetailDO> getRecordsByLocalUser();

    /**
     *@description: 分页获取当前登录用户的记账记录
     */
    List<RecordDetailDO> getRecordsByLocalUserByPage(int pageIndex, int pageSize);

    /**
     *@description: 通过Id获取记账记录
     *@params:
     *@return:
     *@createTime: 2020/6/16 21:40
     *@author: edit by bin
     */
    RecordDetailDO getById(Long id);

    /**
     *@description: 更新
     *@params:
     *@return:
     *@createTime: 2020/6/16 21:50
     *@author: edit by bin
     */
    boolean updateRecord(RecordDetailDO recordDetailDO,CreateOrUpdateRecordDTO dto);

    /**
     *@description: 通过Id删除某条记账记录
     *@params:
     *@return:
     *@createTime: 2020/6/20 19:07
     *@author: edit by bin
     */
    boolean deleteById(Long id);
}
