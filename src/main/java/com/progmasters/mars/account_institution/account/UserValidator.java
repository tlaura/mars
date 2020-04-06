package com.progmasters.mars.account_institution.account;

import com.progmasters.mars.account_institution.account.dto.UserCreationCommand;
import com.progmasters.mars.account_institution.account.service.UserValidatorService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private final UserValidatorService userValidatorService;

    public UserValidator(UserValidatorService userValidatorService) {
        this.userValidatorService = userValidatorService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return UserCreationCommand.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCreationCommand providerAccount = (UserCreationCommand) target;
        userValidatorService.validateFields(providerAccount, errors);
        if (providerAccount.getEmail() != null) {
            if (userValidatorService.emailIsTaken(providerAccount.getEmail())) {
                errors.rejectValue("email", "email.mustBeUnique");
            }
        }
    }
}
