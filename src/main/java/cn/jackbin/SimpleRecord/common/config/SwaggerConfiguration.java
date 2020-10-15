package cn.jackbin.SimpleRecord.common.config;

import cn.jackbin.SimpleRecord.constant.CodeMsg;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.common.config
 * @date: 2020/9/1 19:08
 **/
@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {
    private static List<CodeMsg> list = new ArrayList<>();
    static {
        list.add(CodeMsg.JWT_EXCEPTION);
        list.add(CodeMsg.TOKEN_EXPIRED);
    }

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        List responseMessageList = new ArrayList<>();
        list.forEach(n -> responseMessageList.add(
                new ResponseMessageBuilder().code(n.getRetCode()).message(n.getMessage())
                        .responseModel(new ModelRef(n.getMessage())).build()));
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //分组名称
                .groupName("2.X版本")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("cn.jackbin.SimpleRecord.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
                .globalResponseMessage(RequestMethod.DELETE, responseMessageList);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("简账接口文档")
                .description("服务相关接口")
                .contact(new Contact("bin",null,"itsbintnt@163.com"))
                .version("1.0")
                .build();
    }


}
