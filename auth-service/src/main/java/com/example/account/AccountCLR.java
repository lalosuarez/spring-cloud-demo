package com.example.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * Created by esuarezv on 15/05/2017.
 */
@Component
class AccountCLR implements CommandLineRunner {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountCLR(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Stream.of("esuarez,esuarez1", "test,test1")
                .map( u -> u.split(","))
                .forEach(tuple -> accountRepository.save(new Account(tuple[0], tuple[1], true)));
    }
}