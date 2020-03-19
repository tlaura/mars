package com.progmasters.mars.service;

import com.progmasters.mars.domain.ConfirmationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpirationService {

    @Value("${email.elapsed-hours}")
    private static Integer MAX_ELAPSED_HOURS;
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
//TODO ez így lehet picit olvashatóbb
//            boolean tokenExpired =
//                    confirmationToken
//                            .getDate()
//                            .plusHours(MAX_ELAPSED_HOURS)
//                            .isBefore(LocalDateTime.now());
//            if (tokenExpired) {

                Duration elapsedTime = Duration.between(confirmationToken.getDate(), LocalDateTime.now());
            long difference = Math.abs(elapsedTime.toHours());
            if (!confirmationToken.isConfirmed() && difference >= MAX_ELAPSED_HOURS) {
                emailService.removeConfirmationToken(confirmationToken.getId());
                Long userId = confirmationToken.getUser().getId();
                accountService.removeById(userId);
                logger.info("Account ID removed from db:\t" + userId + "\tElapsed time:\t" + difference);
            }
        }
    }
}
