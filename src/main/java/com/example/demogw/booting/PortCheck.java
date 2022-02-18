package com.example.demogw.booting;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PortCheck implements CommandLineRunner {

    private final ReactiveWebServerApplicationContext server;
    @Override
    public void run(String... args) throws Exception {
        int port = server.getWebServer().getPort();
        log.info("------- port ---------");
        log.info("{}", port);
    }
    
}
