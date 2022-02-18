package com.example.demogw.filter;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

// @Component
public class RouterValidator {

	PathMatcher pathMatcher = new AntPathMatcher();

	public static final List<String> allows = Arrays.asList("/_/**");

	public Predicate<ServerHttpRequest> isAdmin = req -> allows.stream().anyMatch(pattern -> {
		return pathMatcher.match(pattern, req.getPath().toString());
	});

}
