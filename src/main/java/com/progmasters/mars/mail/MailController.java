package com.progmasters.mars.mail;

import com.progmasters.mars.account_institution.account.domain.User;
import com.progmasters.mars.account_institution.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mails/")
@Slf4j
public class MailController {

    private final EmailService emailService;
    private final AccountService accountService;

    @Autowired
    public MailController(EmailService emailService, AccountService accountService) {
        this.emailService = emailService;
        this.accountService = accountService;
    }


    @GetMapping("confirmation/{token}")
    public ResponseEntity<Void> succeedRegister(@PathVariable String token) {
        accountService.confirmUserToken(token);
        log.info("Token Confirmation is requested!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("newPassword")
    public ResponseEntity<Void> newPasswordRequest(@RequestBody String email) {
        User user = accountService.findByEmail(email);
        if (user != null) {
            accountService.setNewPassword(email);
            log.info("New password is requested for:" + email);
        } else {
            log.info("New password is requested for a not existing email:" + email);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("newPassword/{token}")
    public ResponseEntity<Void> createNewPassword(@PathVariable String token, @RequestBody String newPassword) {
        accountService.updatePassword(token, newPassword);
        log.info("");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> sendMail(@RequestBody MailData mailData) {
        emailService.sendEmailToGivenEmail(mailData);
        log.info("Request to send email to: " + mailData.getToEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
