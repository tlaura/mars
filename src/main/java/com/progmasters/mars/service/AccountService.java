package com.progmasters.mars.service;

import com.progmasters.mars.repository.ProviderAccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private ProviderAccountRepository providerAccountRepository;

    public AccountService(ProviderAccountRepository providerAccountRepository) {
        this.providerAccountRepository = providerAccountRepository;
    }
}
