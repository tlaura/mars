package com.progmasters.auti.validation;

import com.progmasters.auti.domain.InstitutionalUser;
import com.progmasters.auti.dto.AddInstitutionForm;
import com.progmasters.auti.repository.InstitutionalUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class AddInstitutionFormValidator implements Validator {

    private InstitutionalUserRepository institutionalUserRepository;

    @Autowired
    public AddInstitutionFormValidator(InstitutionalUserRepository institutionalUserRepository) {
        this.institutionalUserRepository = institutionalUserRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return AddInstitutionForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        AddInstitutionForm form = (AddInstitutionForm) o;
        Optional<InstitutionalUser> creator = institutionalUserRepository.findById(form.getCreatorId());
        if (!creator.isPresent()) {
            errors.rejectValue("creatorId", "institution.creatorNotExists");
        }
    }
}
