package com.example.reservation;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by esuarezv on 15/05/2017.
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

    public String getReservationName() {

    }

    public Collection<String> getReservationNamesFallBack() {
        System.out.println("getReservationNamesFallBack method executed!!!");
        return new ArrayList<>();
    }
}
