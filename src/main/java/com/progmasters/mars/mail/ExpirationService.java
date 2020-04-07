package com.progmasters.mars.mail;

import com.progmasters.mars.account_institution.account.confirmationtoken.ConfirmationToken;
import com.progmasters.mars.account_institution.account.service.AccountService;
import com.progmasters.mars.account_institution.connector.service.AccountInstitutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ExpirationService {

    @Value("${email.elapsed-hours}")
    private Integer MAX_ELAPSED_HOURS;
    private final AccountInstitutionService accountInstitutionService;
    private final AccountService accountService;

    @Autowired
    public ExpirationService(AccountInstitutionService accountInstitutionService, AccountService accountService) {
        this.accountService = accountService;
        this.accountInstitutionService = accountInstitutionService;
    }

    //Every 3 days
    //@Scheduled(cron = "0 0 0 * * 1/3")
    //Every 3 minutes
     @Scheduled(cron = "0 1/3 * * * *")
     //Daily
     // @Scheduled(cron = "0 0 0 * * *")
    private void removeUnconfirmedUsers() {
        List<ConfirmationToken> confirmationTokens = accountService.findAllConfirmationToken();
        for (ConfirmationToken confirmationToken : confirmationTokens) {

            boolean tokenExpired = confirmationToken.getDate().plusHours(MAX_ELAPSED_HOURS).isBefore(LocalDateTime.now());
            if (!confirmationToken.isConfirmed() && tokenExpired) {
                String userEmail = confirmationToken.getUser().getEmail();
                accountService.removeConfirmationToken(confirmationToken.getId());
                accountInstitutionService.deleteAccountByEmail(userEmail);
                log.info("Account removed from db:\t" + userEmail);
            }
        }
    }
}
