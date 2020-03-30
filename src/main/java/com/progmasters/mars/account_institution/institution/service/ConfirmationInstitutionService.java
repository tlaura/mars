package com.progmasters.mars.account_institution.institution.service;

import com.progmasters.mars.account_institution.institution.domain.ConfirmationInstitution;
import com.progmasters.mars.account_institution.institution.dto.InstitutionCreationCommand;
import com.progmasters.mars.account_institution.institution.dto.InstitutionListData;
import com.progmasters.mars.account_institution.institution.repository.ConfirmationInstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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

    public void save(InstitutionCreationCommand institutionCreationCommand, String email) {
        ConfirmationInstitution confirmationInstitution = new ConfirmationInstitution(institutionCreationCommand);
        confirmationInstitution.setProviderEmail(email);
        confirmationInstitutionRepository.save(confirmationInstitution);
    }

    List<ConfirmationInstitution> findAll() {
        return confirmationInstitutionRepository.findAll();
    }

    public List<InstitutionListData> getConfirmationListData() {
        return findAll().stream().map(InstitutionListData::new).collect(Collectors.toList());
    }

    public ConfirmationInstitution findById(Long id) {
        return confirmationInstitutionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Institution not found by id"));
    }

    public void delete(ConfirmationInstitution confirmationInstitution) {
        confirmationInstitutionRepository.delete(confirmationInstitution);
    }


}
