package cn.jackbin.SimpleRecord.common.filter;

import cn.jackbin.SimpleRecord.common.LocalUser;
import cn.jackbin.SimpleRecord.util.SpringContextUtil;
import cn.jackbin.SimpleRecord.common.config.JWTConfig;
import cn.jackbin.SimpleRecord.dto.Result;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.exception.BaseException;
import cn.jackbin.SimpleRecord.exception.NotFoundException;
import cn.jackbin.SimpleRecord.exception.ParameterException;
import cn.jackbin.SimpleRecord.service.UserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 此拦截器进行鉴权操作
 * @date: 2020/8/3 21:04
 **/
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private static List<String> whiteList = new ArrayList<>();

    static {
        whiteList.add("/user/register");
        whiteList.add("/doc.html");
        whiteList.add(".js");
        whiteList.add(".css");
        whiteList.add("/swagger-resources/configuration/ui");
        whiteList.add("/swagger-resources");
        whiteList.add("/api-docs");
    }

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (checkIsWhiteList(request.getServletPath())) {
            chain.doFilter(request, response);
            return;
        }
        String token = request.getHeader("token");
        // 校验token正确性
        try {
            checkToken(token);
            // 如果请求头中有token，则进行解析，并且设置认证信息
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(token));
            super.doFilterInternal(request, response, chain);
        } catch (BaseException e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().write(JSON.toJSONString(Result.error(e.getCodeMsg(), e.getMessage()), SerializerFeature.WriteMapNullValue));
        }
    }

    // 这里从token中获取用户信息并新建一个token
    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        JWTConfig jwtConfig = SpringContextUtil.getBean(JWTConfig.class);
        Claims claims = jwtConfig.getTokenClaim(token);
        if (claims != null){
            String userId = jwtConfig.getUserIdFromToken(claims);
            // 设置当前用户到线程中
            setLocalUser(userId);
            List<String> permissions = jwtConfig.getPermissions(claims);
            List<GrantedAuthority> list = new ArrayList<>();
            permissions.forEach(
                    n-> list.add(new SimpleGrantedAuthority(n))
            );
            return new UsernamePasswordAuthenticationToken(userId, null, list);
        }else {
            throw new ParameterException("token格式不正确");
        }
    }

    /**
     * 校验token的正确性
     */
    private void checkToken(String token) {
        JWTConfig jwtConfig = SpringContextUtil.getBean(JWTConfig.class);
        if(StringUtils.isEmpty(token)){
            throw new NotFoundException(jwtConfig.getHeader()+"不能为空");
        }
        Claims claims = jwtConfig.getTokenClaim(token);
        if(claims == null || jwtConfig.isTokenExpired(claims)){
            throw new ParameterException(jwtConfig.getHeader() + "失效，请重新登录。");
        }
    }

    /**
     * 根据userId设置当前用户
     * @param userId
     */
    private void setLocalUser(String userId) {
        UserService userService = SpringContextUtil.getBean(UserService.class);
        UserDO userDO = userService.getById(userId);
        if (userDO == null) {
            throw new NotFoundException("未找到指定用户");
        }
        LocalUser.setLocalUser(userDO);
    }

    private boolean checkIsWhiteList(String servletPath) {
        String temp = whiteList.stream().filter(servletPath::endsWith).findFirst().orElse(null);
        return StringUtils.isNoneBlank(temp);
    }

}
