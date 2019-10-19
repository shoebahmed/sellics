package com.sellics.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  @Bean
  public Docket productApi() {
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.basePackage("com.sellics.controller")).paths(regex("/api.*"))
        .build().apiInfo(metaData()).securitySchemes(Lists.newArrayList(apiKey()))
        .securityContexts(Arrays.asList(securityContext()));
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder().securityReferences(defaultAuth())
        .forPaths(PathSelectors.regex("/api.*")).build();
  }

  private List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Arrays.asList(new SecurityReference("apiKey", authorizationScopes));
  }

  private ApiKey apiKey() {
    return new ApiKey("apiKey", "Authorization", "header");
  }

  private ApiInfo metaData() {
    return new ApiInfo("GFG REST API", "GFG REST API.", "API TOS", "Terms of service",
        new Contact("Shoeb Ahmed", "www.google.com", "shoebahmed@yahoo.com"), "License of API",
        "API license URL", Collections.emptyList());
  }
}
