package com.webapp.accompanyingparents.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;

@Configuration
@Profile({"local", "dev", "staging"})
@EnableSwagger2
@Import({springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class})
public class SwaggerConfig {
    HashSet<String> consumesAndProduces = new HashSet<>(Arrays.asList("application/json"));

    @Bean
    public Docket qrCodeApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .forCodeGeneration(true)
                .consumes(consumesAndProduces)
                .produces(consumesAndProduces)
                .useDefaultResponseMessages(false)
                .select().apis(RequestHandlerSelectors.basePackage("com.webapp.accompanyingparents.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}