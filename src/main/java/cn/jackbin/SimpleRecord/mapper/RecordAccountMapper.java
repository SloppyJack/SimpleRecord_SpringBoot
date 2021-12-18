package cn.jackbin.SimpleRecord.mapper;

import cn.jackbin.SimpleRecord.common.config.datasource.MyBaseMapper;
import cn.jackbin.SimpleRecord.dto.RecordAccountAnalysisDTO;
import cn.jackbin.SimpleRecord.entity.RecordAccountDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.mapper
 * @date: 2021/7/13 21:54
 **/
@Repository
public interface RecordAccountMapper extends MyBaseMapper<RecordAccountDO> {
    List<RecordAccountAnalysisDTO> queryInAndOutTotal(Integer userId);
}
