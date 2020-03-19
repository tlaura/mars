package com.progmasters.mars.service;

import com.progmasters.mars.domain.Institution;
import com.progmasters.mars.domain.InstitutionType;
import com.progmasters.mars.domain.ProviderAccount;
import com.progmasters.mars.dto.InstitutionListData;
import com.progmasters.mars.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.dto.ProviderUserDetails;
import com.progmasters.mars.dto.ProviderUserDetailsEdit;
import com.progmasters.mars.repository.ProviderAccountRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AccountService {

    private ProviderAccountRepository providerAccountRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AccountService(ProviderAccountRepository providerAccountRepository,
                          BCryptPasswordEncoder passwordEncoder,
                          EmailService emailService) {
        this.providerAccountRepository = providerAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public Long save(ProviderAccountCreationCommand providerAccountCreationCommand) {
        ProviderAccount providerAccount = new ProviderAccount(providerAccountCreationCommand);
        providerAccount.setPassword(passwordEncoder.encode(providerAccountCreationCommand.getPassword()));
        providerAccountRepository.save(providerAccount);


        emailService.sendConfirmationEmail(providerAccount);

        return providerAccount.getId();
    }

    public void removeById(Long id) {
        providerAccountRepository.deleteById(id);
    }

    public ProviderUserDetails getProviderAccount(String loggedInUser) {
        ProviderAccount providerAccount = providerAccountRepository.findByEmail(loggedInUser);
        return new ProviderUserDetails(providerAccount);
    }

    public List<InstitutionListData> getInstitutionsByType(InstitutionType institutionType) {
        List<ProviderAccount> accountListByType = providerAccountRepository.findByType(institutionType);
        List<InstitutionListData> institutionListData = new ArrayList<>();
        for (ProviderAccount providerAccount : accountListByType) {
            for (Institution institution : providerAccount.getInstitutions()) {
                institutionListData.add(new InstitutionListData(institution));
            }
        }
        return institutionListData;
    }


    public ProviderAccount findById(Long id) {
        return providerAccountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No account found by given id"));
    }


    public ProviderUserDetailsEdit getProviderAccountEditDetails(String loggedInUser) {
        ProviderAccount providerAccount = providerAccountRepository.findByEmail(loggedInUser);
        return new ProviderUserDetailsEdit(providerAccount);
    }

    public ProviderAccount updateProviderAccount(ProviderUserDetailsEdit providerUserDetailsEdit, String loggedInUser) {
        ProviderAccount providerAccount = providerAccountRepository.findByEmail(loggedInUser);
        if (providerAccount != null) {
            updateAccountFields(providerUserDetailsEdit, providerAccount);
            return providerAccount;
        } else {
            return null;
        }
    }

    private void updateAccountFields(ProviderUserDetailsEdit providerUserDetailsEdit, ProviderAccount providerAccount) {
        providerAccount.setName(providerUserDetailsEdit.getName());
        providerAccount.setProviderServiceName(providerUserDetailsEdit.getProviderServiceName());
        providerAccount.setPassword(providerUserDetailsEdit.getPassword());
        providerAccount.setPhone(providerUserDetailsEdit.getPhone());
        providerAccount.setZipcode(providerUserDetailsEdit.getZipcode());
        providerAccount.setCity(providerUserDetailsEdit.getCity());
        providerAccount.setAddress(providerUserDetailsEdit.getAddress());
    }
}
