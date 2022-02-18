package com.example.demogw.filter;

import java.util.UUID;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class HeaderGlobalFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest()
        .mutate().header("H-X", UUID.randomUUID().toString()).build();
        
        log.info("---------- header -----------");
        request.getHeaders().forEach((name, value) -> {
            log.info("{}:{}", name, value);
        });

        return chain.filter(exchange.mutate().request(request).build());
    }
    
}
