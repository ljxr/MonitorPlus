package com.enjoyor.common.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Component
@Configuration
@EnableSwagger2
@EnableWebMvc
@ComponentScan(basePackages = {"com.enjoyor"})   
public class SwaggerConfig extends WebMvcConfigurationSupport{

	@Bean
    public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).forCodeGeneration(true).select().apis(RequestHandlerSelectors.basePackage("com.enjoyor.modules.dma.controller"))
                //过滤生成链接
                .paths(PathSelectors.any()).build().apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {

        Contact contact=new Contact("enjoyor","","");
        ApiInfo apiInfo = new ApiInfoBuilder().license("Apache License Version 2.0").title("Swagger 集成测试").description("Swagger API Teste").contact(contact).version("1.0").build();

        return apiInfo;
    }
}
