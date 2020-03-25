package com.progmasters.mars.account_institution.institution.openinghours.service;

import com.progmasters.mars.account_institution.institution.domain.Institution;
import com.progmasters.mars.account_institution.institution.openinghours.domain.OpeningHours;
import com.progmasters.mars.account_institution.institution.openinghours.dto.OpeningHoursCreationCommand;
import com.progmasters.mars.account_institution.institution.openinghours.dto.OpeningHoursData;
import com.progmasters.mars.account_institution.institution.openinghours.repository.OpeningHoursRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OpeningHoursService {

    private final OpeningHoursRepository openingHoursRepository;

    public OpeningHoursService(OpeningHoursRepository openingHoursRepository) {
        this.openingHoursRepository = openingHoursRepository;
    }

    public void saveOpeningHoursToInstitution(OpeningHoursCreationCommand openingHoursCreationCommand, Institution institution) {
        OpeningHours openingHours = new OpeningHours(openingHoursCreationCommand);
        openingHours.setInstitution(institution);
        openingHoursRepository.save(openingHours);
    }

    public List<OpeningHoursData> getOpeningHoursByInstitutionId(Long id) {
        return openingHoursRepository.getOpeningHoursByInstitutionId(id).stream().map(OpeningHoursData::new).collect(Collectors.toList());
    }

    private void updateAndSave(OpeningHours openingHours, Institution institution) {
        openingHours.setInstitution(institution);
        openingHoursRepository.save(openingHours);
    }

    public List<OpeningHours> findAll() {
        return openingHoursRepository.findAll();
    }
}
