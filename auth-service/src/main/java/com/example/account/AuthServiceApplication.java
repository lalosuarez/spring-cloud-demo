package com.example.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@SpringBootApplication
@EnableResourceServer
public class AuthServiceApplication {
	public static void main(String[] args) {
		System.out.println("\n--> PROFILE = " + System.getProperty("spring.profiles.active") + "\n");
		SpringApplication.run(AuthServiceApplication.class, args);
	}
}