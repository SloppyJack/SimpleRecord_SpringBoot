package cn.jackbin.jianzhang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"cn.jackbin.jianzhang"})
@MapperScan(basePackages = {"cn.jackbin.jianzhang"})
public class SpringbootJianzhangApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJianzhangApplication.class, args);
    }

}
