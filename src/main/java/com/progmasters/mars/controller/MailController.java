package com.progmasters.mars.controller;

import com.progmasters.mars.dto.MailData;
import com.progmasters.mars.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mails/")
public class MailController {

    private final EmailService emailService;


    public MailController(EmailService emailService) {
        this.emailService = emailService;
    }


    @GetMapping("confirmation/{token}")
    public ResponseEntity<Void> succeedRegister(@PathVariable String token) {
        emailService.confirmUser(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> sendMail(@RequestBody MailData mailData) {

        emailService.sendEmailToGivenEmail(mailData);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
