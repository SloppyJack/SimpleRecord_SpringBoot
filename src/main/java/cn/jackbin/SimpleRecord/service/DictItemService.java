package cn.jackbin.SimpleRecord.service;

import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.entity.DictItemDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.service
 * @date: 2021/8/4 20:07
 **/
public interface DictItemService extends IService<DictItemDO> {
    List<DictItemDO> getDictItemsByDictId(Integer dictId);

    void getByPage(Integer dictId, PageBO<DictItemDO> pageBO);
}
