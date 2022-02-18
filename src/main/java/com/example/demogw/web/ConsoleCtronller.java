package com.example.demogw.web;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/_")
public class ConsoleCtronller {

	@PostMapping("/apis")
	public ResponseEntity<?> newApi(@RequestBody Map<String, String> reqBdoy) {
		reqBdoy.put("created", Instant.now().toEpochMilli() + "");
		return ResponseEntity.ok().body(reqBdoy);
	}

	@GetMapping
	public Mono<?> remoteCall() {
		WebClient client = WebClient.builder().build();

		return client.get().uri("https://reqres.in/api/users").retrieve().bodyToMono(JsonNode.class)
				.onErrorResume(e -> {
					e.printStackTrace();
					return Mono.empty();
				}).timeout(Duration.ofSeconds(2));
	}

}
