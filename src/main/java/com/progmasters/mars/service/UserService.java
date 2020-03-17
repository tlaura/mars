package com.progmasters.mars.service;

import com.progmasters.mars.repository.IndividualUserRepository;
import com.progmasters.mars.repository.ProviderAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final IndividualUserRepository userRepository;
    private final ProviderAccountRepository providerAccountRepository;
    private final EmailService emailService;

    @Autowired
    public UserService(IndividualUserRepository userRepository, ProviderAccountRepository providerAccountRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.providerAccountRepository = providerAccountRepository;
        this.emailService = emailService;
    }
}
