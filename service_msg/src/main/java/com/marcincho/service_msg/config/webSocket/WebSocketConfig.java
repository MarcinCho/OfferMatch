package com.marcincho.service_msg.config.webSocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcincho.service_msg.filter.WebSocketTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.messaging.context.AuthenticationPrincipalArgumentResolver;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.RequestUpgradeStrategy;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.util.List;

//https://docs.spring.io/spring-security/reference/servlet/integrations/websocket.html



@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("info.marcincho.host")
    private String frontendCallerHost;

    private final WebSocketTokenFilter webSocketTokenFilter;


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
            registry
                    .setApplicationDestinationPrefixes("/app")
                    .enableSimpleBroker("/topic")
                    .setTaskScheduler(heartBeatScheduler())
                    .setHeartbeatValue(new long[] {40000L, 40000L});
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new AuthenticationPrincipalArgumentResolver());
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(webSocketTokenFilter);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        RequestUpgradeStrategy upgradeStrategy = new TomcatRequestUpgradeStrategy();
        registry
                .addEndpoint("/ws")
               .setHandshakeHandler(new DefaultHandshakeHandler(upgradeStrategy))
                .setAllowedOrigins("*");

    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
        resolver.setDefaultMimeType(MediaType.APPLICATION_JSON);
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(new ObjectMapper());
        converter.setContentTypeResolver(resolver);
        messageConverters.add(converter);
        return false;
    }

    @Bean
    public TaskScheduler heartBeatScheduler() {
        return new ThreadPoolTaskScheduler();
    }

}
