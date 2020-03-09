package com.progmasters.mars.service;

import com.progmasters.mars.domain.IndividualUser;
import com.progmasters.mars.repository.IndividualUserRepository;
import com.progmasters.mars.repository.InstitutionalUserRepository;
import com.progmasters.mars.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private IndividualUserRepository userRepository;
    private InstitutionalUserRepository institutionalUserRepository;

    @Autowired
    public UserService(IndividualUserRepository userRepository, InstitutionalUserRepository institutionalUserRepository) {
        this.userRepository = userRepository;
        this.institutionalUserRepository = institutionalUserRepository;
    }

}
