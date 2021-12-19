package cn.jackbin.SimpleRecord.service;

import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.dto.RecordBookAnalysisDTO;
import cn.jackbin.SimpleRecord.entity.RecordBookDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.service
 * @date: 2020/11/23 21:35
 **/
public interface RecordBookService extends IService<RecordBookDO> {

    void getByPage(Integer userId, PageBO<RecordBookAnalysisDTO> pageBO);

    void add(Integer userId, String name, String remark, Integer orderNo);

    void getByName(String name);

    /**
     * 获取用户默认账本
     */
    RecordBookDO getDefaultBook(Integer userId);

    void edit(Long id, Integer userId, String name, String remark, Integer orderNo, Integer isUserDefault);

    /**
     * 更新为默认账单
     */
    void updateDefault(Long defaultId, Long sourceId, Integer userId, String name, String remark, Integer orderNo, Integer isUserDefault);

    /**
     * 获取用户的所有账本
     */
    List<RecordBookDO> getList(Integer userId);
}
