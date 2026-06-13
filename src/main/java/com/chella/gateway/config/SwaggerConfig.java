package com.chella.gateway.config;

import org.springframework.context.annotation.Configuration;

/**
 * Swagger configuration is handled primarily through application.yml and routes,
 * ensuring the Springdoc UI aggregates OpenAPI specifications from downstream services.
 */
@Configuration
public class SwaggerConfig {
    // Aggregation is defined in application.yml properties `springdoc.swagger-ui.urls`
    // and gateway RewritePath filters to ensure proper routing of /v3/api-docs.
}
