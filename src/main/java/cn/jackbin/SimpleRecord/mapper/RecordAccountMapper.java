package cn.jackbin.SimpleRecord.mapper;

import cn.jackbin.SimpleRecord.common.config.datasource.MyBaseMapper;
import cn.jackbin.SimpleRecord.entity.RecordAccountDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.mapper
 * @date: 2021/7/13 21:54
 **/
@Repository
public interface RecordAccountMapper extends MyBaseMapper<RecordAccountDO> {
}
