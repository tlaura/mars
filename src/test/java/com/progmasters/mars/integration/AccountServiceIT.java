package com.progmasters.mars.integration;

import com.progmasters.mars.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback
@AutoConfigureTestDatabase
public class AccountServiceIT {


    @Autowired
    private AccountService accountService;


    @Test
    public void accountTest() {

        Assertions.assertThrows(NullPointerException.class, () -> accountService.getProviderUser("none"));
    }
}
