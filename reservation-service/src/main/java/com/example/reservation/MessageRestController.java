package com.example.reservation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by esuarezv on 15/05/2017.
 */
@RefreshScope
@RestController
class MessageRestController {
    @Value("${message:default message here}")
    private String message;

    @Value("${privateMessage:default private message}")
    private String privateMessage;

    @RequestMapping("/message")
    String getMessage() {
        return this.message;
    }

    @RequestMapping("/privateMessage")
    String getPrivateMessage() {
        return this.privateMessage;
    }
}
