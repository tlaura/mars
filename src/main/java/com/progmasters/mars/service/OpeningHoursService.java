package com.progmasters.mars.service;

import com.progmasters.mars.domain.Institution;
import com.progmasters.mars.domain.OpeningHours;
import com.progmasters.mars.dto.OpeningHoursCreationCommand;
import com.progmasters.mars.dto.OpeningHoursData;
import com.progmasters.mars.repository.OpeningHoursRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<OpeningHoursData> getOpeningHoursByInstitutionId(Long id) {
        return openingHoursRepository.getOpeningHoursByInstitutionId(id).stream().map(OpeningHoursData::new).collect(Collectors.toList());
    }

    private void updateAndSave(OpeningHours openingHours, Institution institution) {
        openingHours.setInstitution(institution);
        openingHoursRepository.save(openingHours);
    }
}
