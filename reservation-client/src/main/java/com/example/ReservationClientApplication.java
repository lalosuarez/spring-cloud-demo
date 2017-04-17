package com.example;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Micro proxy service
 */
@EnableFeignClients
@EnableCircuitBreaker
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class ReservationClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationClientApplication.class, args);
	}
}

@FeignClient("reservation-service")
interface ReservationReader {

    @RequestMapping(method = RequestMethod.GET, value = "/reservations")
    Resources<Reservation> read();
}

/**
 * API Gateway
 */
@RestController
@RequestMapping("/reservations")
class ReservationApiGatewayRestController {

    private final ReservationReader reservationReader;

    @Autowired
    public ReservationApiGatewayRestController(ReservationReader reservationReader) {
        this.reservationReader = reservationReader;
    }

    public Collection<String> getReservationNamesFallBack() {
        System.out.println("getReservationNamesFallBack method executed!!!");
        return new ArrayList<String>();
    }

    @HystrixCommand(fallbackMethod = "getReservationNamesFallBack")
    @RequestMapping(method = RequestMethod.GET, value = "/names")
    public Collection<String> getReservationNames() {
        return this.reservationReader
                .read()
                .getContent()
                .stream()
                .map(Reservation::getReservationName)
                .collect(Collectors.toList());
    }
//    @Autowired
//	private RestTemplate restTemplate;
//
//    /**
//     * Because of an unknown reason @Autowired does not work on RestTemplate
//     * and need this.
//     * @return RestTemplate
//     */
//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
//
//    public Collection<String> getReservationNamesFallBack() {
//        System.out.println("getReservationNamesFallBack method executed!!!");
//        return new ArrayList<String>();
//    }
//
//    @HystrixCommand(fallbackMethod = "getReservationNamesFallBack")
//	@RequestMapping("/names")
//	public Collection<String> getReservationNames() {
//        //System.out.println("getReservationNames executed!!!");
//
//        ParameterizedTypeReference<Resources<Reservation>> ptr = new
//                ParameterizedTypeReference<Resources<Reservation>>() {};
//
//        //System.out.println("ParameterizedTypeReference created!!!");
//
//        ResponseEntity<Resources<Reservation>> responseEntity =
//                this.restTemplate.exchange("http://reservation-service/reservations",
//                        HttpMethod.GET, null, ptr);
//
//        /*System.out.println(responseEntity.toString());
//        System.out.println(responseEntity.getBody());
//        System.out.println(responseEntity.getBody().getContent());*/
//
//        List<String> collect = responseEntity
//                .getBody()
//                .getContent()
//                .stream()
//                .map(Reservation::getReservationName)
//                .collect(Collectors.toList());
//
//        /*for (String s:collect) {
//            System.out.println(s);
//        }*/
//
//        return collect;
//	}
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
