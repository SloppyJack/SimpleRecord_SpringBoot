package cn.jackbin.SimpleRecord.service;

import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.entity.DictDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.service
 * @date: 2021/7/20 20:06
 **/
public interface DictService extends IService<DictDO> {
    void getByPage(String name, String code, Integer status, PageBO<DictDO> pageBO);

    void add(String name, String code, Integer orderNo, String remark);

    void edit(Integer id, String name, String code, Integer orderNo, String remark);

    DictDO getByCode(String code);

    boolean removeById(Long id);

    void reset(Integer id);
}
