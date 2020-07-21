package cn.jackbin.jianzhang.mapper;

import cn.jackbin.jianzhang.entity.UserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jackbin
 * @since 2020-07-21
 */
public interface UserMapper extends BaseMapper<UserDO> {
}
