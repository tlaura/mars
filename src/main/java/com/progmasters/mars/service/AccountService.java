package com.progmasters.mars.service;

import com.progmasters.mars.domain.Institution;
import com.progmasters.mars.domain.ProviderAccount;
import com.progmasters.mars.dto.InstitutionCreationCommand;
import com.progmasters.mars.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.dto.ProviderUserDetails;
import com.progmasters.mars.repository.InstitutionRepository;
import com.progmasters.mars.repository.ProviderAccountRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountService {

    private ProviderAccountRepository providerAccountRepository;
    private final InstitutionRepository institutionRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AccountService(ProviderAccountRepository providerAccountRepository,
                          InstitutionRepository institutionRepository,
                          BCryptPasswordEncoder passwordEncoder,
                          EmailService emailService) {
        this.providerAccountRepository = providerAccountRepository;
        this.institutionRepository = institutionRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public void createProviderAccount(ProviderAccountCreationCommand providerAccountCreationCommand) {
        ProviderAccount providerAccount = new ProviderAccount(providerAccountCreationCommand);
        providerAccount.setPassword(passwordEncoder.encode(providerAccountCreationCommand.getPassword()));
        providerAccount.setInstitutions(createInstitutionList(providerAccountCreationCommand));

        providerAccountRepository.save(providerAccount);
        emailService.sendConfirmationEmail(providerAccount);
    }

    private List<Institution> createInstitutionList(ProviderAccountCreationCommand providerAccountCreationCommand) {
        return providerAccountCreationCommand.getInstitutions().stream()
                .map(institution -> institution.getId() != null ?
                        institutionRepository.findById(institution.getId()).orElseThrow() : createInstitution(institution))
                .collect(Collectors.toList());
    }

    private Institution createInstitution(InstitutionCreationCommand institutionCreationCommand) {
        Institution institution = new Institution(institutionCreationCommand);
        institutionRepository.save(institution);
        return institution;
    }

    public void removeById(Long id) {
        providerAccountRepository.deleteById(id);
    }

    public ProviderUserDetails getProviderAccount(String email) {
        ProviderAccount providerAccount = providerAccountRepository.findByEmail(email);
        return new ProviderUserDetails(providerAccount);
    }
}
