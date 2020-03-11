package com.progmasters.mars.service;

import com.progmasters.mars.repository.IndividualUserRepository;
import com.progmasters.mars.repository.InstitutionalUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final IndividualUserRepository userRepository;
    private final InstitutionalUserRepository institutionalUserRepository;
    private final EmailService emailService;

    @Autowired
    public UserService(IndividualUserRepository userRepository, InstitutionalUserRepository institutionalUserRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.institutionalUserRepository = institutionalUserRepository;
        this.emailService = emailService;
    }
}
