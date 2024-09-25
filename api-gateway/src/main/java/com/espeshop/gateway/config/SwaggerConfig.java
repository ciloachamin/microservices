package com.espeshop.gateway.config;

import static org.springdoc.core.utils.Constants.DEFAULT_API_DOCS_URL;

import jakarta.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Configuration;

@Configuration
class SwaggerConfig {
    private static final Logger log = LoggerFactory.getLogger(SwaggerConfig.class);
    private final RouteDefinitionLocator locator;
    private final SwaggerUiConfigProperties swaggerUiConfigProperties;

    public SwaggerConfig(RouteDefinitionLocator locator, SwaggerUiConfigProperties swaggerUiConfigProperties) {
        this.locator = locator;
        this.swaggerUiConfigProperties = swaggerUiConfigProperties;
    }

    @PostConstruct
    public void init() {
        List<RouteDefinition> definitions =
                locator.getRouteDefinitions().collectList().block();
        Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> urls = new HashSet<>();
        definitions.forEach(routeDefinition -> {
            log.info("Route ID: {}", routeDefinition.getId());  // Log the route ID
        });
        definitions.stream()
                .filter(routeDefinition -> routeDefinition.getId().matches("ms-.*")) // Filtrar por prefijo "ms-"
                .forEach(routeDefinition -> {
                    String name = routeDefinition.getId().replaceAll("ms-", "api/"); // Quitar el prefijo "ms-"
                    log.info("Adding Swagger URL: {}", name);
                    AbstractSwaggerUiConfigProperties.SwaggerUrl swaggerUrl =
                            new AbstractSwaggerUiConfigProperties.SwaggerUrl(
                                    name, DEFAULT_API_DOCS_URL + "/" + name, null);
                    urls.add(swaggerUrl);
                });
        log.info("aaaaaaaa: {}", urls);
        swaggerUiConfigProperties.setUrls(urls);
    }

}
