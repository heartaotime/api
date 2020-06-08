package com.open.custom.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Description: http://127.0.0.1:5555/api/swagger-ui.html
 * Author: huxintao
 * Date: 2020-05-14
 *
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${baseUrlSwigger}")
    private String baseUrlSwigger;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .host(baseUrlSwigger)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis((input) -> {
                    Class<?> declaringClass = input.declaringClass();
                    if (declaringClass.isAnnotationPresent(RestController.class)) {
                        return true;
                    }
                    if (input.isAnnotatedWith(ResponseBody.class)) {
                        return true;
                    }
                    return false;
                })
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //大标题
                .title("接口测试页面")
                //版本
                .version("1.0")
                .build();
    }
}
