package com.example.demogw.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.HttpHandler;

@Configuration
public class PortConfig {
    
    @Autowired
    HttpHandler httpHandler;

    WebServer http;

    @Value("${server.http.port}")
    private int httpPort;

    @PostConstruct
    public void start() {
        ReactiveWebServerFactory factory = new NettyReactiveWebServerFactory(httpPort);
        this.http = factory.getWebServer(this.httpHandler);
        this.http.start();
    }

    @PreDestroy
    public void stop() {
        this.http.stop();
    }

}
