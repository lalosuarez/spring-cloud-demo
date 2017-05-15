package com.example.account;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by esuarezv on 15/05/2017.
 */
@RestController
class PrincipalRestController {
    @RequestMapping("/user")
    Principal principal(Principal principal) {
        System.out.println("Principal = " + principal);
        return principal;
    }
}
