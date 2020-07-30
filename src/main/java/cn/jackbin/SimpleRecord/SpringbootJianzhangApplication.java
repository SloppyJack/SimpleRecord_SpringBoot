package cn.jackbin.SimpleRecord;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.jackbin.SimpleRecord.mapper")
public class SpringbootJianzhangApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJianzhangApplication.class, args);
    }

}
