package com.marcincho.service_msg.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
public class WebConfig implements WebMvcConfigurer {

    public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
        registry
                .addMapping("/**").allowedMethods("*");
    }
}
