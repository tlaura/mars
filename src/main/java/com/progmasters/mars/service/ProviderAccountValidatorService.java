package com.progmasters.mars.service;

import com.progmasters.mars.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.repository.ProviderAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
public class ProviderAccountValidatorService {

    private static final String NAME_REGEX = "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+$";
    private static final String PASSWORD_REGEX = "(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,15})$";
    private static final String EMAIL_REGEX = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final String PHONE_REGEX = "^\\+(\\d{1,2})\\D*(\\d{1,3})\\D*(\\d{3})\\D*(\\d{3,4})$";
    private static final String ZIPCODE_REGEX = "^[1-9][0-9]{3}$";

    private ProviderAccountRepository providerAccountRepository;

    public ProviderAccountValidatorService(ProviderAccountRepository providerAccountRepository) {
        this.providerAccountRepository = providerAccountRepository;
    }

    //    TODO more validators
    public void validateFields(ProviderAccountCreationCommand providerAccount, Errors errors) {
        if (providerAccount.getName() == null) {
            errors.rejectValue("name", "name.mustGive");
        } else if (!providerAccount.getName().matches(NAME_REGEX)) {
            errors.rejectValue("name", "name.invalidFormat");
        }
        if (providerAccount.getProviderServiceName() == null) {
            errors.rejectValue("providerServiceName", "providerServiceName.mustGive");
        } else if (!providerAccount.getProviderServiceName().matches(NAME_REGEX)) {
            errors.rejectValue("providerServiceName", "providerServiceName.invalidFormat");
        }
        if (providerAccount.getPassword() == null) {
            errors.rejectValue("password", "password.mustGive");
        } else if (!providerAccount.getPassword().matches(PASSWORD_REGEX)) {
            errors.rejectValue("password", "password.invalidFormat");
        }
        if (providerAccount.getEmail() == null) {
            errors.rejectValue("email", "email.mustGive");
        } else if (!providerAccount.getEmail().matches(EMAIL_REGEX)) {
            errors.rejectValue("email", "email.invalidFormat");
        }
        if (providerAccount.getPhone() == null) {
            errors.rejectValue("phone", "phone.mustGive");
        } else if (!providerAccount.getPhone().matches(PHONE_REGEX)) {
            errors.rejectValue("phone", "phone.invalidFormat");
        }
        if (providerAccount.getAgeGroupMin() == null || providerAccount.getAgeGroupMin() < 0) {
            errors.rejectValue("ageGroupMin", "ageGroupMin.mustGive");
        }
        if (providerAccount.getAgeGroupMax() == null || providerAccount.getAgeGroupMax() < 0) {
            errors.rejectValue("ageGroupMax", "ageGroupMax.mustGive");
        }
    }

    public boolean nameIsTaken(String name) {
        return !providerAccountRepository.findAllByName(name).isEmpty();
    }

    public boolean emailIsTaken(String email) {
        return !providerAccountRepository.findAllByEmail(email).isEmpty();
    }
}
