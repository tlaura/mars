package com.progmasters.mars.validation;

import com.progmasters.mars.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.repository.ProviderAccountRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProviderAccountValidator implements Validator {

    private ProviderAccountRepository providerAccountRepository;

    public ProviderAccountValidator(ProviderAccountRepository providerAccountRepository) {
        this.providerAccountRepository = providerAccountRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return ProviderAccountCreationCommand.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
//        AddInstitutionForm form = (AddInstitutionForm) o;
//        Optional<InstitutionalUser> creator = institutionalUserRepository.findById(form.getCreatorId());
//        if (creator.isEmpty()) {
//            errors.rejectValue("creatorId", "institution.creatorNotExists");
//        }
    }

}
