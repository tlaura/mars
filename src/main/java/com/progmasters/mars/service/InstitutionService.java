package com.progmasters.mars.service;

import com.progmasters.mars.domain.Institution;
import com.progmasters.mars.dto.InstitutionCreationForm;
import com.progmasters.mars.dto.InstitutionDetails;
import com.progmasters.mars.dto.InstitutionListData;
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

    public List<InstitutionListData> getInstitutionList() {
        //  Pageable pageable= PageRequest.of(page,size);
        return institutionRepository.findAll()
                .stream()
                .map(InstitutionListData::new)
                .collect(Collectors.toList());
    }

    public InstitutionDetails getInstitutionDetails(Long id) {
        Optional<Institution> institution = institutionRepository.findById(id);
        if (institution.isEmpty())
            throw new IllegalArgumentException("There is no institution for this id:" + id);
        return new InstitutionDetails(institution.get());
    }

    public List<InstitutionListData> getInstitutionsBySearchKeyword(String keyword) {
        return institutionRepository.findByNameContainsIgnoreCase(keyword)
                .stream()
                .map(InstitutionListData::new)
                .collect(Collectors.toList());
    }

    public void createInstitution(InstitutionCreationForm institutionCreationForm) {

        Institution institution = new Institution(institutionCreationForm);
        //todo get user id
        institutionRepository.save(institution);
    }
}
