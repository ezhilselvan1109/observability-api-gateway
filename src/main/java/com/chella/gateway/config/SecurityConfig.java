package com.chella.gateway.config;

import com.chella.gateway.exception.GatewayAuthenticationEntryPoint;
import com.chella.gateway.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final GatewayAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        // Public Endpoints
                        .pathMatchers(
                                "/api/users/register",
                                "/api/users/login",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/v3/api-docs/**",
                                "/user-service/v3/api-docs/**",
                                "/order-service/v3/api-docs/**",
                                "/notification-service/v3/api-docs/**",
                                "/actuator/**"
                        ).permitAll()
                        // Protected Endpoints
                        .pathMatchers("/api/orders/**", "/api/notifications/**", "/api/users/profile").authenticated()
                        .anyExchange().authenticated()
                )
                .exceptionHandling(exceptionHandlingSpec -> 
                    exceptionHandlingSpec.authenticationEntryPoint(authenticationEntryPoint)
                )
                .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}
