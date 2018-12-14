package com.example.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableResourceServer
public class AuthServiceApplication {
	public static void main(String[] args) {
		System.out.println("\n--> PROFILE = " + System.getProperty("spring.profiles.active") + "\n");
		SpringApplication.run(AuthServiceApplication.class, args);
	}
}