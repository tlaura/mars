package com.progmasters.mars.service;

import com.progmasters.mars.domain.ProviderAccount;
import com.progmasters.mars.util.ProviderAccountBuilder;
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
public class EmailServiceIT {

    @Autowired
    private EmailService emailService;

    @Test
    public void testSendConfirmationEmail() {
        ProviderAccount account = new ProviderAccountBuilder().setProviderServiceName("nincs")
                .setName("nincs")
                .setPassword("nincs")
                .setEmail("nincs@gmail.com")
                .setZipcode(1089)
                .setCity("budapest")
                .setAddress("nincs")
                .setAgeGroupMax(10)
                .setAgeGroupMin(5)
                .setNewsletter(false)
                .buildProviderAccount();

        emailService.sendConfirmationEmail(account);
    }
}
