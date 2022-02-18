package com.example.demogw.filter;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

// @Component
@Slf4j
public class AdminAuthenticationFilter implements WebFilter {

	@Autowired
	RouterValidator routerValidator;

	public static final String BASIC_AUTH_FORMAT = "basic ";

	private List<String> basicAuthCredentials = Arrays.asList("admin:admin", "admin:1234");
	private List<String> allowIps = Arrays.asList("127.0.0.1");

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		if (!routerValidator.isAdmin.test(exchange.getRequest())) {
			log.debug("skip AdminAuthenticationFilter");
			return chain.filter(exchange);
		}

		ServerHttpResponse response = exchange.getResponse();
		ServerHttpRequest request = exchange.getRequest();
		String clientIp = request.getRemoteAddress().getAddress().getHostAddress();

		if (! allowIps.contains(clientIp)) {
			log.warn(">> not permit IP : {}", clientIp);
			response.setStatusCode(HttpStatus.UNAUTHORIZED);
			return response.setComplete();
		}

		String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		if (! StringUtils.hasText(authorization)) {
			log.warn(">> not found Authorization");
			response.setStatusCode(HttpStatus.UNAUTHORIZED);
			return response.setComplete();
		}

		boolean isValidFormat = StringUtils.startsWithIgnoreCase(authorization, BASIC_AUTH_FORMAT);
		if (!isValidFormat) {
			log.warn(">> wrong Basic authorization");
			response.setStatusCode(HttpStatus.BAD_REQUEST);
			return response.setComplete();
		}

		log.info("auth: {}", authorization);
		String encoedAuth = authorization.substring(BASIC_AUTH_FORMAT.length());
		String credentials = new String(Base64.getDecoder().decode(encoedAuth));
		boolean isAllow = checkAuthetication(credentials);

		if (!isAllow) {
			log.warn(">> not found basic credentials. {}", credentials);
			response.setStatusCode(HttpStatus.FORBIDDEN);
			return response.setComplete();
		}

		return chain.filter(exchange);
	}

	private boolean checkAuthetication(String credentials) {
		return basicAuthCredentials.contains(credentials);
	}

}
