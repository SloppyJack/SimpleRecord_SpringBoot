package cn.jackbin.SimpleRecord.service;

import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.entity.RecordUserCategoryDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.service
 * @date: 2020/11/23 21:35
 **/
public interface RecordUserCategoryService extends IService<RecordUserCategoryDO> {

    List<RecordUserCategoryDO> getList(Integer userId);

    void getByPage(Integer userId, String recordTypeCode, PageBO<RecordUserCategoryDO> pageBO);

    void reset(Integer userId);

    void saveOrUpdate(Long id, Integer userId, String recordTypeCode, String name, Integer orderNo);
}
