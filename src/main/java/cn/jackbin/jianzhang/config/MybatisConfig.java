package cn.jackbin.jianzhang.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.jianzhang.config
 * @date: 2020/7/21 21:15
 **/
@EnableTransactionManagement
@Configuration
public class MybatisConfig {
    /**mybatis-plus 分页插件*/
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
