package com.progmasters.mars.account_institution.institution.service;

import com.progmasters.mars.account_institution.institution.dto.InstitutionCreationCommand;
import com.progmasters.mars.account_institution.institution.repository.ConfirmationInstitutionRepository;
import com.progmasters.mars.account_institution.institution.repository.InstitutionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
public class InstitutionValidatorService {

    @Value("${regex.validator.email}")
    private String email;
    @Value("${regex.validator.phone}")
    private String phone;

    private InstitutionRepository institutionRepository;
    private ConfirmationInstitutionRepository confirmationInstitutionRepository;

    public InstitutionValidatorService(InstitutionRepository institutionRepository, ConfirmationInstitutionRepository confirmationInstitutionRepository) {
        this.institutionRepository = institutionRepository;
        this.confirmationInstitutionRepository = confirmationInstitutionRepository;
    }

    public void validateFields(InstitutionCreationCommand institution, Errors errors) {
        if (institution.getName() == null) {
            errors.rejectValue("name", "name.mustGive");
        }
        if (institution.getPhone() == null) {
            errors.rejectValue("phone", "phone.mustGive");
        } else if (!institution.getPhone().matches(phone)) {
            errors.rejectValue("phone", "phone.invalidFormat");
        }
        if (institution.getEmail() == null) {
            errors.rejectValue("email", "email.mustGive");
        } else if (!institution.getEmail().matches(email)) {
            errors.rejectValue("email", "email.invalidFormat");
        }
        if (institution.getDescription() == null) {
            errors.rejectValue("description", "description.mustGive");
        } else if (institution.getDescription().length() < 30) {
            errors.rejectValue("description", "description.short");
        } else if (institution.getDescription().length() > 200) {
            errors.rejectValue("description", "description.long");
        }
    }

    public boolean nameIsTaken(String name) {
        return (!institutionRepository.findAllByName(name).isEmpty() || !confirmationInstitutionRepository.findAllByName(name).isEmpty());
    }
}
