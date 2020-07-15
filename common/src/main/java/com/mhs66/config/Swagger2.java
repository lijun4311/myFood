package com.mhs66.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *description:
 *  接口文档配置
 *  访问地址 swagger-ui.html或 doc.html
 *@author 76442
 *@date 2020-07-15 18:52
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
    /**
     * 配置swagger2核心配置 docket
     *
     * @return 配置注入
     */
    @Bean
    public Docket createRestApi() {
        // 指定api类型为swagger2
        return new Docket(DocumentationType.SWAGGER_2)
                // 用于定义api文档汇总信息
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors
                        // 指定controller包
                        .basePackage("com.mhs66.controller"))
                // 所有controller
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 文档页标题
                .title("吃货商城api")
                // 联系人信息
                .contact(new Contact("lijun",
                        "https://www.mhs66.cn",
                        "764424765@QQ.com"))
                // 详细信息
                .description("商城api文档")
                // 文档版本号
                .version("1.0.1")
                // 网站地址
                .termsOfServiceUrl("https://www.mhs66.cn")
                .build();
    }

}
