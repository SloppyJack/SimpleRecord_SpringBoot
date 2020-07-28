package cn.jackbin.jianzhang.common.aop;

import cn.jackbin.jianzhang.common.LocalUser;
import cn.jackbin.jianzhang.common.config.JWTConfig;
import cn.jackbin.jianzhang.dto.CodeMsg;
import cn.jackbin.jianzhang.entity.UserDO;
import cn.jackbin.jianzhang.exception.NotFoundException;
import cn.jackbin.jianzhang.exception.ParameterException;
import cn.jackbin.jianzhang.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.jianzhang.common.aop
 * @date: 2020/7/28 20:20
 **/
@Component
@Aspect
@Slf4j
public class LoginRequiredAspect {
    @Autowired
    private JWTConfig jwtConfig;
    @Autowired
    private UserService userService;

    private String token;
    private Long userId;

    @Pointcut("@annotation(cn.jackbin.jianzhang.common.ioc.LoginRequired)")
    private void doHandler(){
        // step 3
        UserDO userDO = userService.getById(userId);
        if (userDO == null) {
            throw new NotFoundException("未找到指定用户");
        }
        LocalUser.setLocalUser(userDO);
    }

    // 开始环绕
    @Around("doHandler()")
    public void around(ProceedingJoinPoint joinPoint)throws Throwable{
        // step 1
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        // 从 http 请求头中取出 token
        token = request.getHeader("token");

        // 切面往下执行
        joinPoint.proceed();
    }

    @Before("doHandler()")
    public void before(){
        // step 2
        // 进行token校验
        verifyHeader(token);
    }

    @After("doHandler()")
    public void after(){
        // step 4
        log.info("userId:{}通过token验证",userId);
    }

    private void verifyHeader(String token) {
        if (StringUtils.isBlank(token)) {
            throw new ParameterException(CodeMsg.PARAMETER_ILLEGAL,"token不能为空");
        }
        Claims claims = jwtConfig.getTokenClaim(token);
        if (claims == null) {
            throw new ParameterException(CodeMsg.PARAMETER_ILLEGAL,"token不合法");
        }
        // 校验token是否过期
        if (jwtConfig.isTokenExpired(claims)) {
            throw new ParameterException(CodeMsg.PARAMETER_ILLEGAL,"token已过期");
        }
        userId = Long.valueOf(jwtConfig.getUserIdFromToken(claims));
    }
}
