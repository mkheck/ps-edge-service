package com.thehecklers.psedgeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Created by markheckler on 9/25/15.
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private DataHandler dataHandler;
    @Autowired
    private ControlHandler controlHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(dataHandler, "/data").setAllowedOrigins("*");
        registry.addHandler(controlHandler, "/control").setAllowedOrigins("*");
    }
}
