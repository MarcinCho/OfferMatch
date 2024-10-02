package com.marcincho.service_msg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ServiceMsgApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceMsgApplication.class, args);
    }

}
