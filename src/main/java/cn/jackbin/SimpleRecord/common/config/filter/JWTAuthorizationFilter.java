package cn.jackbin.SimpleRecord.common.config.filter;

import cn.jackbin.SimpleRecord.common.LocalUserId;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.exception.BusinessException;
import cn.jackbin.SimpleRecord.utils.SpringContextUtil;
import cn.jackbin.SimpleRecord.common.config.sercurity.JWTConfig;
import cn.jackbin.SimpleRecord.vo.Result;
import cn.jackbin.SimpleRecord.exception.BaseException;
import cn.jackbin.SimpleRecord.exception.ParameterException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
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
 * @description: 此过滤器进行鉴权操作
 * @date: 2020/8/3 21:04
 **/
@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("token");
        // 校验token正确性
        try {
            checkToken(token);
            // 如果请求头中有token，则进行解析，并且设置认证信息
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(token));
            super.doFilter(request, response, chain);
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
            // 设置当前用户Id到线程中
            LocalUserId.set(Long.valueOf(userId));
            List<String> permissions = jwtConfig.getPermissions(claims);
            List<GrantedAuthority> list = new ArrayList<>();
            permissions.forEach(
                    n-> list.add(new SimpleGrantedAuthority(n))
            );
            return new UsernamePasswordAuthenticationToken(userId, null, list);
        }else {
            log.error("token格式不正确");
            throw new BusinessException(CodeMsg.JWT_EXCEPTION);
        }
    }

    /**
     * 校验token的正确性
     */
    private void checkToken(String token) {
        JWTConfig jwtConfig = SpringContextUtil.getBean(JWTConfig.class);
        if(StringUtils.isEmpty(token)){
            log.error("{}不能为空",jwtConfig.getHeader());
            throw new BusinessException(CodeMsg.JWT_EXCEPTION);
        }
        Claims claims = jwtConfig.getTokenClaim(token);
        if(claims == null){
            log.error(CodeMsg.JWT_EXCEPTION.getMessage());
            throw new BusinessException(CodeMsg.JWT_EXCEPTION);
        }
        if (jwtConfig.isTokenExpired(claims)) {
            log.error(CodeMsg.TOKEN_EXPIRED.getMessage());
            throw new ParameterException(CodeMsg.TOKEN_EXPIRED);
        }
    }

}
