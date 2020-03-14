package com.progmasters.mars.controller;

import com.progmasters.mars.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/confirmations")
public class MailController {

    private final EmailService emailService;


    public MailController(EmailService emailService) {
        this.emailService = emailService;
    }


    @GetMapping("/{token}")
    public ResponseEntity<Void> succeedRegister(@PathVariable String token) {
        emailService.confirmUser(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
