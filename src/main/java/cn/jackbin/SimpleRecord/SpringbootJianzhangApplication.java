package cn.jackbin.SimpleRecord;

import cn.jackbin.SimpleRecord.common.SpringContextUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@MapperScan("cn.jackbin.SimpleRecord.mapper")
public class SpringbootJianzhangApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringbootJianzhangApplication.class, args);
        SpringContextUtil.setApplicationContext(context);
    }

}
