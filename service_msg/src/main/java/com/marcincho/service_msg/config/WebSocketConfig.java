package com.marcincho.service_msg.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final WebSocketTokenFilter webSocketTokenFilter;

    public WebSocketConfig(WebSocketTokenFilter webSocketTokenFilter) {
        this.webSocketTokenFilter = webSocketTokenFilter;
    }

    public void configureClientInboundChannel

}
