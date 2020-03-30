package com.progmasters.mars.account_institution.institution.service;

import com.progmasters.mars.account_institution.institution.domain.ConfirmationInstitution;
import com.progmasters.mars.account_institution.institution.dto.ConfirmationInstitutionListData;
import com.progmasters.mars.account_institution.institution.dto.InstitutionCreationCommand;
import com.progmasters.mars.account_institution.institution.repository.ConfirmationInstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ConfirmationInstitutionService {

    private final ConfirmationInstitutionRepository confirmationInstitutionRepository;

    @Autowired
    public ConfirmationInstitutionService(ConfirmationInstitutionRepository confirmationInstitutionRepository) {
        this.confirmationInstitutionRepository = confirmationInstitutionRepository;
    }

    public void save(InstitutionCreationCommand institutionCreationCommand) {
        ConfirmationInstitution confirmationInstitution = new ConfirmationInstitution(institutionCreationCommand);
        confirmationInstitutionRepository.save(confirmationInstitution);
    }

    List<ConfirmationInstitution> findAll() {
        return confirmationInstitutionRepository.findAll();
    }

    public List<ConfirmationInstitutionListData> getConfirmationListData() {
        return findAll().stream().map(ConfirmationInstitutionListData::new).collect(Collectors.toList());
    }


}
