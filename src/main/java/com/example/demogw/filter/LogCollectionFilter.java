package com.example.demogw.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LogCollectionFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // TODO Auto-generated method stub
        log.info("---- resquest .. ");

        ServerHttpRequest request = exchange.getRequest();
        log.info("path: {}", request.getPath().toString());
        log.info("method: {}", request.getMethod().toString());
        log.info("client ip: {}", request.getRemoteAddress().getAddress().getHostAddress());
        long startTime = System.currentTimeMillis();
        log.info("request time: {}", startTime);

        return chain.filter(exchange)
        .then(Mono.fromRunnable(() -> {
            log.info("resposne *****");
            ServerHttpResponse resposne = exchange.getResponse();
            log.info("status : {}", resposne.getStatusCode().value());
            log.info("response time : {}", System.currentTimeMillis());
            log.info("duration time: {}", (System.currentTimeMillis() - startTime));

            Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
            log.info("route: {}", route);
        }));
    }
    
}
