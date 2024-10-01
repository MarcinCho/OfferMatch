package com.marcincho.service_msg.config;

import com.marcincho.service_msg.utils.JWTUtils;
import org.springframework.messaging.support.ChannelInterceptor;

import javax.swing.event.ChangeListener;

public class WebSocketTokenFilter implements ChannelInterceptor {
    private final JWTUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;

    public WebSocketTokenFilter(JWTUtils jwtUtils, Us) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetails
    }
}
