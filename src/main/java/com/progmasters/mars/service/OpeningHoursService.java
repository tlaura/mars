package com.progmasters.mars.service;

import com.progmasters.mars.domain.Institution;
import com.progmasters.mars.domain.OpeningHours;
import com.progmasters.mars.dto.OpeningHoursCreationCommand;
import com.progmasters.mars.repository.OpeningHoursRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OpeningHoursService {

    private final OpeningHoursRepository openingHoursRepository;
    private final InstitutionService institutionService;

    public OpeningHoursService(OpeningHoursRepository openingHoursRepository, InstitutionService institutionService) {
        this.openingHoursRepository = openingHoursRepository;
        this.institutionService = institutionService;
    }

    public void saveToInstitution(List<OpeningHoursCreationCommand> openingHoursList, Long institutionId) {
        Institution institution = institutionService.findById(institutionId);
        openingHoursList.stream().map(OpeningHours::new).forEach(openingHours -> updateAndSave(openingHours, institution));
    }

    private void updateAndSave(OpeningHours openingHours, Institution institution) {
        openingHours.setInstitution(institution);
        openingHoursRepository.save(openingHours);
    }
}
