package com.example.reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * Micro proxy service
 */
@IntegrationComponentScan
@EnableResourceServer
@EnableBinding(ReservationChannels.class)
@EnableFeignClients
@EnableZuulProxy
@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
public class ReservationClientApplication {
	public static void main(String[] args) {
		System.out.println("\n--> PROFILE = " + System.getProperty("spring.profiles.active") + "\n");
		SpringApplication.run(ReservationClientApplication.class, args);
	}
}






