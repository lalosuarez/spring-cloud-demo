package com.example;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.hateoas.Resources;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Micro proxy service
 */
@IntegrationComponentScan
@EnableBinding(ReservationChannels.class)
@EnableFeignClients
@EnableZuulProxy
@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
public class ReservationClientApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReservationClientApplication.class, args);
	}
}

interface ReservationChannels {
    @Output
    MessageChannel output();
}

@FeignClient("reservation-service")
interface ReservationReader {
    @RequestMapping(method = RequestMethod.GET, value = "/reservations")
    Resources<Reservation> read();
}

@MessagingGateway
interface ReservationWriter {
    @Gateway(requestChannel = "output")
    void write(String rn);
}

/**
 * API Gateway
 */
@RestController
@RequestMapping("/reservations")
class ReservationApiGatewayRestController {

    private final ReservationReader reservationReader;
    private final ReservationWriter reservationWriter;

    @Autowired
    public ReservationApiGatewayRestController(ReservationReader reservationReader, ReservationWriter reservationWriter) {
        this.reservationReader = reservationReader;
        this.reservationWriter = reservationWriter;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void write(@RequestBody Reservation reservation) {
        this.reservationWriter.write(reservation.getReservationName());
    }

    @HystrixCommand(fallbackMethod = "getReservationNamesFallBack")
    @RequestMapping(method = RequestMethod.GET, value = "/names")
    public Collection<String> getReservationNames() {
        //System.out.println("getReservationNames executed");

        return this.reservationReader
                .read()
                .getContent()
                .stream()
                .map(Reservation::getReservationName)
                .collect(Collectors.toList());
    }

    public Collection<String> getReservationNamesFallBack() {
        System.out.println("getReservationNamesFallBack method executed!!!");
        return new ArrayList<String>();
    }
}

class Reservation {

    private String reservationName;

    public String getReservationName() {
        return reservationName;
    }

    public void setReservationName(String reservationName) {
        this.reservationName = reservationName;
    }
}
