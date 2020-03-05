package com.progmasters.mars.service;

import com.progmasters.mars.email.EmailService;
import com.progmasters.mars.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    @Value("${email.send.subject}")
    private String subject;
    @Value("${email.send.text}")
    private String text;

    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public void saveUser() {

        //TODO get mail from parameter, add hash
        emailService.sendMsg("", subject, text);

    }
}
