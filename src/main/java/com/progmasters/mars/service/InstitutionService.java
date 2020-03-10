package com.progmasters.mars.service;

import com.progmasters.mars.domain.Institution;
import com.progmasters.mars.dto.AddInstitutionForm;
import com.progmasters.mars.dto.InstitutionDetails;
import com.progmasters.mars.dto.InstitutionListItem;
import com.progmasters.mars.repository.InstitutionRepository;
import com.progmasters.mars.repository.InstitutionalUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstitutionService {
    private InstitutionRepository institutionRepository;

    private InstitutionalUserRepository institutionalUserRepository;

    @Autowired
    public InstitutionService(InstitutionRepository institutionRepository, InstitutionalUserRepository institutionalUserRepository) {
        this.institutionRepository = institutionRepository;
        this.institutionalUserRepository = institutionalUserRepository;
    }

    public List<InstitutionListItem> getInstitutionList() {
        return institutionRepository.findAll()
                .stream()
                .map(InstitutionListItem::new)
                .collect(Collectors.toList());
    }

    public InstitutionDetails getInstitutionDetails(Long id) {
        Optional<Institution> institution = institutionRepository.findById(id);
        if (institution.isEmpty())
            throw new IllegalArgumentException("There is no institution for this id:" + id);
        return new InstitutionDetails(institution.get());
    }

    public List<InstitutionListItem> getInstitutionsBySearchKeyword(String keyword) {
        return institutionRepository.findByNameContainsIgnoreCase(keyword)
                .stream()
                .map(InstitutionListItem::new)
                .collect(Collectors.toList());
    }

    public void createInstitution(AddInstitutionForm form) {
//        Optional<InstitutionalUser> creator = institutionalUserRepository.findById(form.getCreatorId());
//        if (creator.isEmpty())
//            throw new IllegalArgumentException("There is no institutional user for this id:" + form.getCreatorId());
        Institution institution = new Institution();
        institution.setName(form.getName());
        institution.setEmail(form.getEmail());
        institution.setZipCode(form.getZipCode());
        institution.setCity(form.getCity());
        institution.setAddress(form.getAddress());
        institution.setDescription(form.getDescription());
//        institution.setCreator(creator.get());
        institutionRepository.save(institution);
    }

    public List<InstitutionDetails> getInstitutionDetailsList() {
        return institutionRepository.findAll()
                .stream()
                .map(InstitutionDetails::new)
                .collect(Collectors.toList());
    }
}
