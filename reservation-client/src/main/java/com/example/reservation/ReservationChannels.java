package com.example.reservation;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * Created by esuarezv on 15/05/2017.
 */
interface ReservationChannels {
    @Output
    MessageChannel output();
}
