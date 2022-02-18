package com.example.demogw.filter;

import java.net.URI;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class HostNameFilter implements GlobalFilter {
    private String varName = "HOSTNAME";
    private String targetName = "reqres.in";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Route route = (Route) exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        if (!route.getUri().toString().contains(varName)) {
            return chain.filter(exchange);
        }

        URI newUri;
		try {
            newUri = UriComponentsBuilder.fromUri(route.getUri())
            .host(targetName)
            .path("/api/users/2")
            .build().toUri();

            log.info("path: {}", route.getUri().getRawPath());
            log.info("--- url : {}", newUri);
			ServerHttpRequest newRequest = exchange.getRequest().mutate().uri(newUri).build();
			ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
            newExchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, newRequest.getURI());

            return chain.filter(newExchange);
		} catch (Exception e) {
			e.printStackTrace();
            return Mono.empty();
		}
    }	
}
