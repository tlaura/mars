package com.progmasters.mars.account.service;

import com.progmasters.mars.account.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.account.repository.ProviderAccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
public class ProviderAccountValidatorService {

    @Value("${regex.validator.name}")
    private String name;
    @Value("${regex.validator.password}")
    private String password;
    @Value("${regex.validator.email}")
    private String email;
    @Value("${regex.validator.phone}")
    private String phone;

    private ProviderAccountRepository providerAccountRepository;

    public ProviderAccountValidatorService(ProviderAccountRepository providerAccountRepository) {
        this.providerAccountRepository = providerAccountRepository;
    }

    //    TODO more validators
    public void validateFields(ProviderAccountCreationCommand providerAccount, Errors errors) {
        if (providerAccount.getName() == null) {
            errors.rejectValue("name", "name.mustGive");
        } else if (!providerAccount.getName().matches(name)) {
            errors.rejectValue("name", "name.invalidFormat");
        }
        if (providerAccount.getProviderServiceName() == null) {
            errors.rejectValue("providerServiceName", "providerServiceName.mustGive");
        } else if (!providerAccount.getProviderServiceName().matches(name)) {
            errors.rejectValue("providerServiceName", "providerServiceName.invalidFormat");
        }
        if (providerAccount.getPassword() == null) {
            errors.rejectValue("password", "password.mustGive");
        } else if (!providerAccount.getPassword().matches(password)) {
            errors.rejectValue("password", "password.invalidFormat");
        }
        if (providerAccount.getEmail() == null) {
            errors.rejectValue("email", "email.mustGive");
        } else if (!providerAccount.getEmail().matches(email)) {
            errors.rejectValue("email", "email.invalidFormat");
        }
        if (providerAccount.getPhone() == null) {
            errors.rejectValue("phone", "phone.mustGive");
        } else if (!providerAccount.getPhone().matches(phone)) {
            errors.rejectValue("phone", "phone.invalidFormat");
        }
        if (providerAccount.getAgeGroupMin() == null || providerAccount.getAgeGroupMin() < 0) {
            errors.rejectValue("ageGroupMin", "ageGroupMin.mustGive");
        }
        if (providerAccount.getAgeGroupMax() == null || providerAccount.getAgeGroupMax() < 0) {
            errors.rejectValue("ageGroupMax", "ageGroupMax.mustGive");
        }
    }

    public boolean emailIsTaken(String email) {
        return !providerAccountRepository.findAllByEmail(email).isEmpty();
    }
}
