package com.progmasters.mars.mail;

import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import com.progmasters.mars.account_institution.account.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mails/")
public class MailController {

    private final EmailService emailService;
    private final AccountService accountService;
    private final Logger logger = LoggerFactory.getLogger(MailController.class);

    @Autowired
    public MailController(EmailService emailService, AccountService accountService) {
        this.emailService = emailService;
        this.accountService = accountService;
    }


    @GetMapping("confirmation/{token}")
    public ResponseEntity<Void> succeedRegister(@PathVariable String token) {
        accountService.confirmUserToken(token);
        logger.info("Token Confirmation is requested!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("new-password")
    public ResponseEntity<Void> newPasswordRequest(@RequestBody String email) {
        ProviderAccount providerAccount = accountService.findByEmail(email);
        if (providerAccount != null) {
            accountService.setNewPassword(email);
            logger.info("New password is requested for:" + email);
        } else {
            logger.info("New password is requested for a not existing email:" + email);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("new-password/{token}")
    public ResponseEntity<Void> createNewPassword(@PathVariable String token, @RequestBody String newPassword) {
        accountService.updatePassword(token, newPassword);
        logger.info("");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> sendMail(@RequestBody MailData mailData) {

        emailService.sendEmailToGivenEmail(mailData);
        logger.info("Request to send email to: " + mailData.getToEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
