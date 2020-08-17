package cn.jackbin.SimpleRecord.common.filter;

import cn.jackbin.SimpleRecord.common.SpringContextUtil;
import cn.jackbin.SimpleRecord.common.config.JWTConfig;
import cn.jackbin.SimpleRecord.exception.ParameterException;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 此拦截器进行鉴权操作
 * @date: 2020/8/3 21:04
 **/
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("token");
        // 如果请求头中没有Authorization信息则直接放行了
        if (StringUtils.isBlank(token)) {
            chain.doFilter(request, response);
            return;
        }
        // 如果请求头中有token，则进行解析，并且设置认证信息
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(token));
        super.doFilterInternal(request, response, chain);
    }

    // 这里从token中获取用户信息并新建一个token
    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        JWTConfig jwtConfig = SpringContextUtil.getBean(JWTConfig.class);
        Claims claims = jwtConfig.getTokenClaim(token);
        if (claims != null){
            String userId = jwtConfig.getUserIdFromToken(claims);
            String role = jwtConfig.getRoleName(claims);
            return new UsernamePasswordAuthenticationToken(userId, null, Collections.singleton(new SimpleGrantedAuthority(role)));
        }else {
            throw new ParameterException("token格式不正确");
        }
    }

}
