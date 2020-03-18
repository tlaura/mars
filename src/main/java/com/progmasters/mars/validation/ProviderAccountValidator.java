package com.progmasters.mars.validation;

import com.progmasters.mars.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.service.ProviderAccountValidatorService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProviderAccountValidator implements Validator {

    private final ProviderAccountValidatorService validatorService;

    public ProviderAccountValidator(ProviderAccountValidatorService validatorService) {
        this.validatorService = validatorService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return ProviderAccountCreationCommand.class.equals(aClass);
    }


    //TODO: fix validators
    @Override
    public void validate(Object o, Errors errors) {
        if (o != null) {
            ProviderAccountCreationCommand providerAccount = (ProviderAccountCreationCommand) o;
            validatorService.validateFields(providerAccount, errors);
            if (providerAccount.getName() != null) {
                if (validatorService.nameIsTaken(providerAccount.getName())) {
                    errors.rejectValue("name", "name.mustBeUnique");
                }
            }
            if (providerAccount.getEmail() != null) {
                if (validatorService.emailIsTaken(providerAccount.getEmail())) {
                    errors.rejectValue("email", "email.mustBeUnique");
                }
            }
        }
    }
}

