package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.entity.RecordTypeDO;
import cn.jackbin.SimpleRecord.mapper.RecordTypeMapper;
import cn.jackbin.SimpleRecord.service.RecordTypeService;
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
public class RecordTypeServiceImpl extends ServiceImpl<RecordTypeMapper, RecordTypeDO> implements RecordTypeService {
    @Autowired
    private RecordTypeMapper recordTypeMapper;

    @Override
    public List<RecordTypeDO> findAll() {
        return recordTypeMapper.selectList(null);
    }
}
