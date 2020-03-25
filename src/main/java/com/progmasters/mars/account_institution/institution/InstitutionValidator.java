package com.progmasters.mars.account_institution.institution;

import com.progmasters.mars.account_institution.institution.dto.InstitutionCreationCommand;
import com.progmasters.mars.account_institution.institution.service.InstitutionValidatorService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class InstitutionValidator implements Validator {

    private final InstitutionValidatorService validatorService;

    public InstitutionValidator(InstitutionValidatorService institutionValidatorService) {
        this.validatorService = institutionValidatorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return InstitutionCreationCommand.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target != null) {
            InstitutionCreationCommand institutionCreationCommand = (InstitutionCreationCommand) target;
            validatorService.validateFields(institutionCreationCommand, errors);
            if (institutionCreationCommand.getName() != null) {
                if (validatorService.nameIsTaken(institutionCreationCommand.getName())) {
                    errors.rejectValue("name", "name.mustBeUnique");
                }
            }
        }
    }
}
