package cn.jackbin.SimpleRecord.mapper;

import cn.jackbin.SimpleRecord.entity.PermissionDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jackbin
 * @since 2020-07-21
 */
@Repository
public interface PermissionMapper extends BaseMapper<PermissionDO> {

    List<PermissionDO> queryPermissionByUserId(Long userId);

}
