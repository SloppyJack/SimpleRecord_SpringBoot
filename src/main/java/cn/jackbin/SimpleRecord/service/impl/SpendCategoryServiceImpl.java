package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.dto.SpendCategoryDTO;
import cn.jackbin.SimpleRecord.entity.SpendCategoryDO;
import cn.jackbin.SimpleRecord.mapper.SpendCategoryMapper;
import cn.jackbin.SimpleRecord.service.SpendCategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jackbin
 * @since 2020-07-21
 */
@Service
public class SpendCategoryServiceImpl extends ServiceImpl<SpendCategoryMapper, SpendCategoryDO> implements SpendCategoryService {
    @Autowired
    private SpendCategoryMapper spendCategoryMapper;

    @Override
    public List<SpendCategoryDTO> findAll() {
        return spendCategoryMapper.queryAll();
    }

    @Override
    public List<SpendCategoryDO> getByRecordTypeId(int recordTypeId) {
        QueryWrapper<SpendCategoryDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("record_type_id",recordTypeId);
        return spendCategoryMapper.selectList(queryWrapper);
    }
}
