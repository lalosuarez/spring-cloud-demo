package com.example.reservation;

import com.example.reservation.Reservation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by esuarezv on 15/05/2017.
 */
@FeignClient("reservation-service")
interface ReservationReader {
    @RequestMapping(method = RequestMethod.GET, value = "/reservations")
    Resources<Reservation> read();
}
