package com.espeshop.users.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
class OpenAPI3Configuration {

    @Value("${swagger.api-gateway-url}")
    String apiGatewayUrl;

    @Bean
    OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Service APIs")
                        .description("Marketplace User Service APIs")
                        .version("v1.0.0")
                        .contact(new Contact().name("TucTech").email("smrtuc32@gmail.com")))
                .servers(List.of(new Server().url(apiGatewayUrl)));
    }
}