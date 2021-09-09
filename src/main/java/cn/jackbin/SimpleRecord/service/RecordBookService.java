package cn.jackbin.SimpleRecord.service;

import cn.jackbin.SimpleRecord.entity.RecordBookDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.service
 * @date: 2020/11/23 21:35
 **/
public interface RecordBookService extends IService<RecordBookDO> {

    void add(Integer userId, String name, String remark, Integer orderNo);

    void getByName(String name);

    /**
     * 获取用户默认账本
     */
    RecordBookDO getDefaultBook(Integer userId);

    void edit(Long id, Integer userId, String name, String remark, Integer orderNo, Integer isUserDefault);
}
