package cn.jackbin.SimpleRecord.mapper;

import cn.jackbin.SimpleRecord.common.config.datasource.MyBaseMapper;
import cn.jackbin.SimpleRecord.entity.DictItemDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.mapper
 * @date: 2021/8/4 20:09
 **/
@Repository
public interface DictItemMapper extends MyBaseMapper<DictItemDO> {
}
