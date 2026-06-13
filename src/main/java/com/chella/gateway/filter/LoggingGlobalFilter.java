package com.chella.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long startTime = System.currentTimeMillis();
        String requestMethod = exchange.getRequest().getMethodValue();
        String requestUrl = exchange.getRequest().getURI().getPath();

        log.info("Incoming Request:\n{} {}", requestMethod, requestUrl);

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            long executionTime = System.currentTimeMillis() - startTime;
            int responseStatus = exchange.getResponse().getStatusCode() != null ? 
                    exchange.getResponse().getStatusCode().value() : 0;
            
            log.info("Response:\n{} \nExecution Time:\n{} ms", responseStatus, executionTime);
        }));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
