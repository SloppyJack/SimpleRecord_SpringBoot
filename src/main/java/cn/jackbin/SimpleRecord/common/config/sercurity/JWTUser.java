package cn.jackbin.SimpleRecord.common.config.sercurity;

import cn.jackbin.SimpleRecord.vo.UserGroupVO;
import com.alibaba.fastjson.JSON;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.common.config.sercurity
 * @date: 2020/8/3 20:16
 **/
public class JWTUser implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public JWTUser(UserGroupVO userGroupVO) {
        this.id = userGroupVO.getUserDO().getId();
        this.username = userGroupVO.getUserDO().getUsername();
        this.password = userGroupVO.getUserIdentityDO().getCredential();
        // 设置当前用户的角色
        this.authorities = Collections.singleton(new SimpleGrantedAuthority(userGroupVO.getGroupDO().getName()));
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
