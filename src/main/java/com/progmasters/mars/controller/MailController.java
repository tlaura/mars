package com.progmasters.mars.controller;

import com.progmasters.mars.dto.MailData;
import com.progmasters.mars.service.EmailService;
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
    private final Logger logger = LoggerFactory.getLogger(MailController.class);

    @Autowired
    public MailController(EmailService emailService) {
        this.emailService = emailService;
    }


    @GetMapping("confirmation/{token}")
    public ResponseEntity<Void> succeedRegister(@PathVariable String token) {
        emailService.confirmUser(token);
        logger.info("Token Confirmation is requested!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> sendMail(@RequestBody MailData mailData) {

        emailService.sendEmailToGivenEmail(mailData);
        logger.info("Request to send email to: " + mailData.getToEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
