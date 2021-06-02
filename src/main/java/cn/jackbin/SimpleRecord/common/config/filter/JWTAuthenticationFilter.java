package cn.jackbin.SimpleRecord.common.config.filter;

import cn.jackbin.SimpleRecord.common.config.sercurity.JWTConfig;
import cn.jackbin.SimpleRecord.common.config.sercurity.JWTUser;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.vo.LoginVO;
import cn.jackbin.SimpleRecord.vo.LoginSuccessVO;
import cn.jackbin.SimpleRecord.vo.Result;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.service.UserService;
import cn.jackbin.SimpleRecord.utils.SpringContextUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
 * @description: 该过滤器用于授权
 * @date: 2020/8/3 20:47
 **/
@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 从输入流中获取到登录的信息
        try {
            LoginVO loginVO = new ObjectMapper().readValue(request.getInputStream(), LoginVO.class);
            return authenticationManager.authenticate(
                    // 此处需要将密码加密验证
                    new UsernamePasswordAuthenticationToken(loginVO.getUsername(), loginVO.getPassword())
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 成功调用的方法
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        JWTUser jwtUser = (JWTUser) authResult.getPrincipal();
        log.info("JWTUser:{}",jwtUser.toString());
        List<String> permissionList = new ArrayList<>();
        jwtUser.getAuthorities().forEach(n -> permissionList.add(n.getAuthority()));
        // 通过获取Spring上下文来获取JWTConfig对象
        JWTConfig jwtConfig = SpringContextUtil.getBean(JWTConfig.class);
        String token = jwtConfig.createToken(jwtUser.getId().toString(),permissionList);
        UserService userService = SpringContextUtil.getBean(UserService.class);
        UserDO userDO = userService.getById(jwtUser.getId());
        LoginSuccessVO vo = new LoginSuccessVO();
        BeanUtils.copyProperties(userDO, vo);
        vo.setToken(token);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(JSON.toJSONString(Result.success(vo)));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(JSON.toJSONString(Result.error(CodeMsg.LOGIN_ERROR), SerializerFeature.WriteMapNullValue));
    }
}
