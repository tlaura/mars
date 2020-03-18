package com.progmasters.mars.service;

import com.progmasters.mars.domain.Institution;
import com.progmasters.mars.domain.InstitutionType;
import com.progmasters.mars.domain.ProviderAccount;
import com.progmasters.mars.dto.InstitutionListData;
import com.progmasters.mars.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.dto.ProviderUserDetails;
import com.progmasters.mars.repository.ProviderAccountRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountService {

    private ProviderAccountRepository providerAccountRepository;
    private final InstitutionService institutionService;
    private BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AccountService(ProviderAccountRepository providerAccountRepository,
                          InstitutionService institutionService,
                          BCryptPasswordEncoder passwordEncoder,
                          EmailService emailService) {
        this.providerAccountRepository = providerAccountRepository;
        this.institutionService = institutionService;
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

    //todo reheck
    private List<Institution> createInstitutionList(ProviderAccountCreationCommand providerAccountCreationCommand) {
        return providerAccountCreationCommand.getInstitutions().stream()
                .map(institution -> institution.getId() != null ?
                        institutionService.findById(institution.getId()) : institutionService.createInstitution(institution))
                .collect(Collectors.toList());
    }

    public void removeById(Long id) {
        providerAccountRepository.deleteById(id);
    }

    public ProviderUserDetails getProviderAccount(String email) {
        ProviderAccount providerAccount = providerAccountRepository.findByEmail(email);
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
}
