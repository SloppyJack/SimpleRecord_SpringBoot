package cn.jackbin.SimpleRecord.common.config.sercurity;

import cn.jackbin.SimpleRecord.bo.UserMenuBO;
import cn.jackbin.SimpleRecord.constant.MenuConstants;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.common.config.sercurity
 * @date: 2020/8/3 20:16
 **/
public class JWTUser implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public JWTUser(UserMenuBO userPermissionBO) {
        this.id = userPermissionBO.getUserDO().getId();
        this.username = userPermissionBO.getUserDO().getUsername();
        this.password = userPermissionBO.getUserDO().getCredential();
        // 设置当前用户的权限集合
        List<GrantedAuthority> list = new ArrayList<>();
        userPermissionBO.getMenuDOList().stream()
                // 跳过空权限字符（注：菜单和目录没有权限字符）
                .filter(n -> StringUtils.isNoneBlank(n.getPermissionSign()))
                .forEach(n-> list.add(new SimpleGrantedAuthority(n.getPermissionSign())));
        this.authorities = list;
    }

    /**
     * 获取鉴权用户的角色
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * 获取账户是否过期
     * @return true 默认为true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否锁定
     * @return true 默认为true
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 账户凭证是否过期
     * @return true 默认为true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账号是否禁用
     * @return true 默认为true
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
