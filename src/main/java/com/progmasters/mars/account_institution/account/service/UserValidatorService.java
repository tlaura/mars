package com.progmasters.mars.account_institution.account.service;

import com.progmasters.mars.account_institution.account.dto.UserCreationCommand;
import com.progmasters.mars.account_institution.account.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
public class UserValidatorService {

    private final UserRepository userRepository;
    @Value("${regex.validator.password}")
    private String password;
    @Value("${regex.validator.email}")
    private String email;
    @Value("${regex.validator.phone}")
    private String phone;

    public UserValidatorService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateFields(UserCreationCommand user, Errors errors) {
        if (user.getName() == null) {
            errors.rejectValue("name", "name.mustGive");
        }
        if (user.getPassword() == null) {
            errors.rejectValue("password", "password.mustGive");
        } else if (!user.getPassword().matches(password)) {
            errors.rejectValue("password", "password.invalidFormat");
        }
        if (user.getEmail() == null) {
            errors.rejectValue("email", "email.mustGive");
        } else if (!user.getEmail().matches(email)) {
            errors.rejectValue("email", "email.invalidFormat");
        }
        if (user.getPhone() == null) {
            errors.rejectValue("phone", "phone.mustGive");
        } else if (!user.getPhone().matches(phone)) {
            errors.rejectValue("phone", "phone.invalidFormat");
        }
    }

    public boolean emailIsTaken(String email) {
        return !userRepository.findAllByEmail(email).isEmpty();
    }
}
