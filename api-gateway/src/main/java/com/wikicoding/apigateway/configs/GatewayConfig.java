package com.wikicoding.apigateway.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableHystrix
public class GatewayConfig {
    private AuthenticationFilter filter;

    @Autowired
    public GatewayConfig(AuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("todo-ms", r -> r.path("/api/v1/todos/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://todo-ms"))
                .route("jwt-auth", r -> r.path("/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://jwt-auth"))
                .build();
    }
}
