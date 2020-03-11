package com.progmasters.mars.service;

import com.progmasters.mars.domain.Institution;
import com.progmasters.mars.domain.OpeningHours;
import com.progmasters.mars.domain.ProviderAccount;
import com.progmasters.mars.dto.InstitutionDetailsData;
import com.progmasters.mars.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.repository.InstitutionRepository;
import com.progmasters.mars.repository.OpeningHoursRepository;
import com.progmasters.mars.repository.ProviderAccountRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private ProviderAccountRepository providerAccountRepository;
    private final InstitutionRepository institutionRepository;
    private OpeningHoursRepository openingHoursRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public AccountService(ProviderAccountRepository providerAccountRepository, InstitutionRepository institutionRepository, OpeningHoursRepository openingHoursRepository, BCryptPasswordEncoder passwordEncoder) {
        this.providerAccountRepository = providerAccountRepository;
        this.institutionRepository = institutionRepository;
        this.openingHoursRepository = openingHoursRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createProviderAccount(ProviderAccountCreationCommand providerAccountCreationCommand) {
        ProviderAccount providerAccount = new ProviderAccount(providerAccountCreationCommand);
        providerAccount.setPassword(passwordEncoder.encode(providerAccountCreationCommand.getPassword()));

        providerAccount.setInstitutions(createInstitutionList(providerAccountCreationCommand));
        providerAccount.setOpeningHours(createOpeningHoursList(providerAccountCreationCommand));

        providerAccountRepository.save(providerAccount);
    }

    private List<OpeningHours> createOpeningHoursList(ProviderAccountCreationCommand providerAccountCreationCommand) {
        return providerAccountCreationCommand.getOpeningHours().stream()
                .map((openingHoursCreationCommand) -> {
                    OpeningHours openingHours = new OpeningHours(openingHoursCreationCommand);
                    openingHoursRepository.save(openingHours);
                    return openingHours;
                })
                .collect(Collectors.toList());
    }

    private List<Institution> createInstitutionList(ProviderAccountCreationCommand providerAccountCreationCommand) {
        return providerAccountCreationCommand.getInstitutions().stream()
                .map(institution -> institution.getId() != null ?
                        institutionRepository.findById(institution.getId()).orElseThrow() : createInstitution(institution))
                .collect(Collectors.toList());
    }

    private Institution createInstitution(InstitutionDetailsData institutionDetailsData) {
        Institution institution = new Institution(institutionDetailsData);
        institutionRepository.save(institution);
        return institution;
    }

}
