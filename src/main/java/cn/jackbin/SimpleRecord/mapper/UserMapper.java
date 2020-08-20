package cn.jackbin.SimpleRecord.mapper;

import cn.jackbin.SimpleRecord.entity.UserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jackbin
 * @since 2020-07-21
 */
@Repository
public interface UserMapper extends BaseMapper<UserDO> {
}
