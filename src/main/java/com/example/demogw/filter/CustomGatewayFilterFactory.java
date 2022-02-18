package com.example.demogw.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomGatewayFilterFactory implements GatewayFilterFactory {

	@Override
	public GatewayFilter apply(Object config) {
		return (exchange, chain) -> {
			log.info("------- custom filter ");
			exchange.getPrincipal().log().subscribe();
			return chain.filter(exchange);
		};
	}

}
