package cn.jackbin.SimpleRecord.common.config.datasource;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.common.config
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

    /**
     * 自定义sql注入器
     */
    @Bean
    public MySqlInjector mySqlInjector(){
        return new MySqlInjector();
    }
}
