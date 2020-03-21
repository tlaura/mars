package com.progmasters.mars.service;

import com.progmasters.mars.domain.InstitutionType;
import com.progmasters.mars.domain.ProviderAccount;
import com.progmasters.mars.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.dto.ProviderUserDetails;
import com.progmasters.mars.dto.ProviderUserDetailsEdit;
import com.progmasters.mars.repository.ProviderAccountRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class AccountService {

    private ProviderAccountRepository providerAccountRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public AccountService(ProviderAccountRepository providerAccountRepository,
                          BCryptPasswordEncoder passwordEncoder
    ) {
        this.providerAccountRepository = providerAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    ProviderAccount save(ProviderAccountCreationCommand providerAccountCreationCommand) {
        ProviderAccount providerAccount = new ProviderAccount(providerAccountCreationCommand);
        providerAccount.setPassword(passwordEncoder.encode(providerAccountCreationCommand.getPassword()));
        providerAccountRepository.save(providerAccount);


        return providerAccount;
    }

    void removeById(Long id) {
        ProviderAccount account = findById(id);
        account.setTypes(null);

        providerAccountRepository.deleteById(id);
    }

    public ProviderUserDetails getProviderAccountByEmail(String loggedInUserEmail) {
        return new ProviderUserDetails(findByEmail(loggedInUserEmail));
    }

    List<ProviderAccount> getAccountsByType(InstitutionType institutionType) {
        return providerAccountRepository.findByType(institutionType);
    }


    ProviderAccount findById(Long id) {
        return providerAccountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No account found by given id:\t" + id));
    }

    ProviderAccount findByEmail(String email) {
        return providerAccountRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("No account found by given email:\t" + email));
    }


    public ProviderUserDetailsEdit getProviderAccountEditDetailsByEmail(String loggedInUserEmail) {
        return new ProviderUserDetailsEdit(findByEmail(loggedInUserEmail));
    }

    public ProviderUserDetailsEdit updateProviderAccount(ProviderUserDetailsEdit providerUserDetailsEdit, String loggedInUserEmail) {
        ProviderAccount providerAccount = findByEmail(loggedInUserEmail);
        updateAccountFields(providerUserDetailsEdit, providerAccount);

        return new ProviderUserDetailsEdit(providerAccount);
    }

    List<ProviderAccount> findAllAccounts() {
        return providerAccountRepository.findAll();
    }

    void updateAccountFields(ProviderUserDetailsEdit providerUserDetailsEdit, ProviderAccount providerAccount) {
        providerAccount.setName(providerUserDetailsEdit.getName());
        providerAccount.setProviderServiceName(providerUserDetailsEdit.getProviderServiceName());
//        providerAccount.setPassword(providerUserDetailsEdit.getPassword());
        providerAccount.setPhone(providerUserDetailsEdit.getPhone());
        providerAccount.setZipcode(providerUserDetailsEdit.getZipcode());
        providerAccount.setCity(providerUserDetailsEdit.getCity());
        providerAccount.setAddress(providerUserDetailsEdit.getAddress());
        providerAccount.setNewsletter(providerUserDetailsEdit.getNewsletter());
    }
}
