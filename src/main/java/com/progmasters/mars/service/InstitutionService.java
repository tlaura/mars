package com.progmasters.mars.service;

import com.progmasters.mars.domain.Institution;
import com.progmasters.mars.domain.InstitutionType;
import com.progmasters.mars.dto.GeoLocationData;
import com.progmasters.mars.dto.InstitutionCreationForm;
import com.progmasters.mars.dto.InstitutionDetailsData;
import com.progmasters.mars.dto.InstitutionListData;
import com.progmasters.mars.repository.InstitutionRepository;
import com.progmasters.mars.repository.InstitutionalUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
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

    public InstitutionDetailsData getInstitutionDetails(Long id) {
        return new InstitutionDetailsData(findById(id));
    }

//    public List<InstitutionListData> getInstitutionsBySearchKeyword(String keyword) {
//        return institutionRepository.findByNameContainsIgnoreCase(keyword)
//                .stream()
//                .map(InstitutionListData::new)
//                .collect(Collectors.toList());
//    }

    public void createInstitution(InstitutionCreationForm institutionCreationForm) {

        Institution institution = new Institution(institutionCreationForm);
        //todo get user id
        institutionRepository.save(institution);
    }

    public List<InstitutionListData> getInstitutionsByType(InstitutionType institutionType) {
        return institutionRepository.findAllByInstitutionType(institutionType).stream().map(InstitutionListData::new).collect(Collectors.toList());
    }

    public void updateInstitutionLocation(GeoLocationData geoLocationData, Long id) {
        Institution institution = findById(id);

        institution.setLongitude(geoLocationData.getLongitude());
        institution.setLatitude(geoLocationData.getLatitude());

        institutionRepository.save(institution);
    }

    private Institution findById(Long id) {
        return institutionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("no such entity found"));
    }
}
