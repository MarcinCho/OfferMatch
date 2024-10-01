package com.marcincho.service_msg.utils;

import org.springframework.beans.factory.annotation.Value;



public class JWTUtils {

    @Value("info.marcincho.secret")
    private String jwtSecret;




}
