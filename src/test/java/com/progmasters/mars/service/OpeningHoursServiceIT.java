package com.progmasters.mars.service;

import com.progmasters.mars.dto.InstitutionCreationCommand;
import com.progmasters.mars.dto.OpeningHoursCreationCommand;
import com.progmasters.mars.util.InstitutionBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Transactional
@Rollback
@AutoConfigureTestDatabase
public class OpeningHoursServiceIT {

    private InstitutionCreationCommand institution;

    private List<OpeningHoursCreationCommand> openingHoursList;

    @Autowired
    private OpeningHoursService openingHoursService;
    @Autowired
    private InstitutionService institutionService;

    @BeforeEach
    public void init() {
        this.institution = createInstitution();
        this.openingHoursList = createOpeningHoursList();
        institutionService.createInstitution(institution);
        institutionService.findByName(institution.getName());
        openingHoursService.saveToInstitution(openingHoursList, institution);
    }

    @Test
    public void testSaveToInstitution_returnsTrue() {
        assertFalse(openingHoursService.findAll().isEmpty());
    }


    private List<OpeningHoursCreationCommand> createOpeningHoursList() {
        List<OpeningHoursCreationCommand> openingHours = new ArrayList<>();
        openingHours.add(new OpeningHoursCreationCommand("Hétfő", LocalTime.of(10, 30), LocalTime.of(12, 30)));
        openingHours.add(new OpeningHoursCreationCommand("Kedd", LocalTime.of(8, 00), LocalTime.of(17, 00)));
        return openingHours;
    }

    private InstitutionCreationCommand createInstitution() {

        return new InstitutionBuilder().setName("ElzaTestInstitution")
                .setEmail("test@test.com")
                .setZipcode(3700)
                .setCity("Kazincbarcika")
                .setAddress("Május 1 út 12.")
                .setDescription("This is a small test description for institution.")
                .setPhone("+36303334444")
                .setWebsite("www.test.com")
                .setOpeningHoursCreationCommands(new ArrayList<>())
                .buildInstitutionCreationCommand();

    }
}
