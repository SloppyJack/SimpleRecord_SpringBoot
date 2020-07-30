package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.entity.GroupDO;
import cn.jackbin.SimpleRecord.mapper.GroupMapper;
import cn.jackbin.SimpleRecord.service.GroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jackbin
 * @since 2020-07-21
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {

}
