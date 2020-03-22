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

    public OpeningHoursService(OpeningHoursRepository openingHoursRepository) {
        this.openingHoursRepository = openingHoursRepository;
    }

    public void saveToInstitution(List<OpeningHoursCreationCommand> openingHoursList, Institution institution) {
        openingHoursList.stream().map(OpeningHours::new).forEach(openingHours -> updateAndSave(openingHours, institution));
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
