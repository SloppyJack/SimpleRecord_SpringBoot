package cn.jackbin.jianzhang.common.config;

import cn.jackbin.jianzhang.common.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.jianzhang.common.config
 * @date: 2020/7/22 21:57
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor).
                addPathPatterns("/**")
                .excludePathPatterns("/*/login");
    }
}
