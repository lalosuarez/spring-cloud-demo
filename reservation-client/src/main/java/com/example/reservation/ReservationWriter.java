package com.example.reservation;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

/**
 * Created by esuarezv on 15/05/2017.
 */
@MessagingGateway
interface ReservationWriter {
    @Gateway(requestChannel = "output")
    void write(String rn);
}
