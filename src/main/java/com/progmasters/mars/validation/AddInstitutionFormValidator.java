package com.progmasters.mars.validation;

import com.progmasters.mars.dto.AddInstitutionForm;
import com.progmasters.mars.repository.InstitutionalUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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
//        AddInstitutionForm form = (AddInstitutionForm) o;
//        Optional<InstitutionalUser> creator = institutionalUserRepository.findById(form.getCreatorId());
//        if (creator.isEmpty()) {
//            errors.rejectValue("creatorId", "institution.creatorNotExists");
//        }
    }

}
