package cn.jackbin.SimpleRecord.service;

import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.entity.UserGroupDO;
import cn.jackbin.SimpleRecord.vo.UserVO;
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
public interface UserService extends IService<UserDO> {

    /**
     * 根据用户名获取用户
     */
    UserDO getByName(String userName);

    /**
     * 根据Id获取指定用户
     */
    UserDO getById(Long id);

    /**
     * 获取所有用户
     */
    List<UserDO> getUsers();

    /**
     * 分页获取用户
     */
    List<UserDO> getByPage(int pageIndex, int pageSize);
}
