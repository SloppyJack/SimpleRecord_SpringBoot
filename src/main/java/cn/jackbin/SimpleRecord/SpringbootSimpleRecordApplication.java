package cn.jackbin.SimpleRecord;

import cn.jackbin.SimpleRecord.util.SpringContextUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@MapperScan("cn.jackbin.SimpleRecord.mapper")
public class SpringbootSimpleRecordApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringbootSimpleRecordApplication.class, args);
        SpringContextUtil.setApplicationContext(context);
    }

}
