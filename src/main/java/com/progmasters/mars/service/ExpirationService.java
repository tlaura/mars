package com.progmasters.mars.service;

import com.progmasters.mars.domain.ConfirmationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpirationService {

    private static final Integer MAX_ELAPSED_DAY = 72;
    private final Logger logger = LoggerFactory.getLogger(ExpirationService.class);
    private final EmailService emailService;
    private final AccountService accountService;

    public ExpirationService(EmailService emailService, AccountService accountService) {
        this.emailService = emailService;
        this.accountService = accountService;
    }

    //Every 3 days
    //@Scheduled(cron = "0 0 0 * * 1/3")
    //Every 3 minutes
    // @Scheduled(cron = "0 1/3 * * * *")
    //Daily
    @Scheduled(cron = "0 0 0 * * *")
    private void removeUnconfirmedUsers() {
        List<ConfirmationToken> confirmationTokens = emailService.findAllConfirmationToken();
        for (ConfirmationToken confirmationToken : confirmationTokens) {
            Duration elapsedTime = Duration.between(confirmationToken.getDate(), LocalDateTime.now());
            long difference = Math.abs(elapsedTime.toHours());
            if (!confirmationToken.isConfirmed() && difference >= MAX_ELAPSED_DAY) {
                emailService.removeConfirmationToken(confirmationToken.getId());
                Long userId = confirmationToken.getUser().getId();
                accountService.removeById(userId);
                logger.info("Account ID removed from db:\t" + userId + "\tElapsed time:\t" + difference);
            }
        }
    }
}
