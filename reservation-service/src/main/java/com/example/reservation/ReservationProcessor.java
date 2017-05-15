package com.example.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

/**
 * Created by esuarezv on 15/05/2017.
 */
@MessageEndpoint
final class ReservationProcessor {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationProcessor(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @ServiceActivator(inputChannel = "input")
    public void onNewReservation(String reservationName) {
        this.reservationRepository.save(new Reservation(reservationName));
    }
}
