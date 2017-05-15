package com.example.reservation;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * Created by esuarezv on 15/05/2017.
 */
public interface ReservationChannels {
    @Input
    SubscribableChannel input();
}
