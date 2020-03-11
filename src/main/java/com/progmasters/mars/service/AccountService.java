package com.progmasters.mars.service;

import com.progmasters.mars.domain.ProviderAccount;
import com.progmasters.mars.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.repository.ProviderAccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private ProviderAccountRepository providerAccountRepository;

    public AccountService(ProviderAccountRepository providerAccountRepository) {
        this.providerAccountRepository = providerAccountRepository;
    }

    public void createProviderAccount(ProviderAccountCreationCommand providerAccountCreationCommand) {
        ProviderAccount providerAccount = new ProviderAccount();
        providerAccount.setName(providerAccountCreationCommand.getName());
        providerAccount.setZipcode(providerAccountCreationCommand.getZipcode());
        providerAccount.setCity(providerAccountCreationCommand.getCity());
        providerAccount.setAddress(providerAccountCreationCommand.getAddress());
        providerAccount.setEmail(providerAccountCreationCommand.getEmail());
        providerAccount.setPhone(providerAccountCreationCommand.getPhone());
        providerAccount.setAgeGroupMin(providerAccountCreationCommand.getAgeGroupMin());
        providerAccount.setAgeGroupMax(providerAccountCreationCommand.getAgeGroupMax());
        providerAccount.setType(providerAccountCreationCommand.getType());
        providerAccount.setNewsletter(providerAccountCreationCommand.getNewsletter());

        providerAccount.setUsername(providerAccountCreationCommand.getUsername());
/*        providerAccount.setPassword(); // TODO: hash

        providerAccount.setInstitutions();
        providerAccount.setOpeningHours();*/

        providerAccountRepository.save(providerAccount);
    }
}
