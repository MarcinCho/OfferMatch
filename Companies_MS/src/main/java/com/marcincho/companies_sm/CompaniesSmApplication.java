package com.marcincho.companies_sm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class CompaniesSmApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompaniesSmApplication.class, args);
	}

}
