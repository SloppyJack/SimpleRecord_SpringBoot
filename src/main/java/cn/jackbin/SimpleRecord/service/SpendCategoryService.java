package cn.jackbin.SimpleRecord.service;

import cn.jackbin.SimpleRecord.entity.SpendCategoryDO;
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
public interface SpendCategoryService extends IService<SpendCategoryDO> {
    /**
     * 列出所有花费分类
     * @return
     */
    List<SpendCategoryDO> findAll();

    /**
     * 通过记账类别Id获取花费分类
     * @param recordTypeId
     * @return
     */
    List<SpendCategoryDO> getByRecordTypeId(int recordTypeId);
}
