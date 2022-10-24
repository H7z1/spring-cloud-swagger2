package com.lzh.auth.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author linzhihao
 * @Date 2022/10/23 11:50 上午
 * @Description
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean(name = "swagger2-auth")
    public Docket createRestApi() {

        //=====添加head参数start============================
        ParameterBuilder parameter1 = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        parameter1.name("Authorization").description("Authorization令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(parameter1.build());
        // =========添加head参数end===================

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lzh"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars).groupName("swagger2-auth"); // 分组

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("auth-接口文档")
                .description("接口文档-鉴权服务")
                .termsOfServiceUrl("http://localhost:8080/doc.html")
                .contact(new Contact("lzh", "", ""))
                .version("v1.0")
                .build();
    }


}
