package com.blog_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "Blog Application : Backend Code",
description = "This is developed by Sanjeet",
termsOfService = "Terms of Service",
version = "1.0",
license = @io.swagger.v3.oas.annotations.info.License(name = "License of APIs"),
contact = @io.swagger.v3.oas.annotations.info.Contact(name = "Sanjeet Kumar",
url ="http://localhost:8080/v3/api-docs",
email = "sanjeetkumar@gmail.com")))
@Configuration
public class OpenApiConfig {
	
	@Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
        		 .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                 .components(new io.swagger.v3.oas.models.Components()
                     .addSecuritySchemes("Bearer Authentication", 
                         new SecurityScheme()
                             .type(SecurityScheme.Type.HTTP)
                             .scheme("bearer")
                             .bearerFormat("JWT")));
     }
    }

