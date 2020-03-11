package com.progmasters.mars.service;

import com.progmasters.mars.domain.Institution;
import com.progmasters.mars.domain.InstitutionType;
import com.progmasters.mars.dto.*;
import com.progmasters.mars.repository.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InstitutionService {

    private final InstitutionRepository institutionRepository;
    private final GeocodeService geocodeService;

    @Autowired
    public InstitutionService(InstitutionRepository institutionRepository, GeocodeService geocodeService) {
        this.institutionRepository = institutionRepository;
        this.geocodeService = geocodeService;
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


    public void createInstitution(InstitutionCreationForm institutionCreationForm) {
        String address = institutionCreationForm.getZipCode() + " " + institutionCreationForm.getCity() + " " + institutionCreationForm.getAddress();
        GeoLocation geoLocation = geocodeService.getGeoLocation(address);
        Institution institution = new Institution(institutionCreationForm, geoLocation);
        //todo get user id
        institutionRepository.save(institution);
    }

    public List<InstitutionListData> getInstitutionsByType(InstitutionType institutionType) {
        return institutionRepository.findAllByInstitutionType(institutionType).stream().map(InstitutionListData::new).collect(Collectors.toList());
    }

    //todo remove in the future
    public List<InstitutionListData> tempByType(InstitutionType institutionType) {
        List<InstitutionListData> listByType = institutionRepository.findAllByInstitutionType(institutionType).stream().map(InstitutionListData::new).collect(Collectors.toList());
        setLocation(listByType);
        return listByType;
    }

    public List<InstitutionListData> tempInstitutionList() {
        List<InstitutionListData> collect = institutionRepository.findAll()
                .stream()
                .map(InstitutionListData::new)
                .collect(Collectors.toList());
        setLocation(collect);

        return collect;
    }

    private void setLocation(List<InstitutionListData> collect) {
        for (InstitutionListData institution : collect) {
            String address = institution.getZipCode() + " " + institution.getCity() + " " + institution.getAddress();
            GeoLocation geoLocation = geocodeService.getGeoLocation(address);
            institution.setLatitude(geoLocation.getLatitude());
            institution.setLongitude(geoLocation.getLongitude());
        }
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
