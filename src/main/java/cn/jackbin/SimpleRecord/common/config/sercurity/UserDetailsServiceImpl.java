package cn.jackbin.SimpleRecord.common.config.sercurity;

import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.service.UserGroupService;
import cn.jackbin.SimpleRecord.service.UserService;
import cn.jackbin.SimpleRecord.vo.UserGroupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.common.config.sercurity
 * @date: 2020/8/3 20:15
 **/
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserGroupService userGroupService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDO userDO = userService.getUserByUserName(s);
        UserGroupVO userGroupVO = userGroupService.getUserGroupVOByUserId(userDO.getId());
        return new JWTUser(userGroupVO);
    }
}
