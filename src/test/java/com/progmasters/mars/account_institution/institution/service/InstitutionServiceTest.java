package com.progmasters.mars.account_institution.institution.service;

import com.google.maps.errors.NotFoundException;
import com.progmasters.mars.account_institution.institution.InstitutionBuilder;
import com.progmasters.mars.account_institution.institution.domain.Institution;
import com.progmasters.mars.account_institution.institution.dto.InstitutionCreationCommand;
import com.progmasters.mars.account_institution.institution.dto.InstitutionDetailsData;
import com.progmasters.mars.account_institution.institution.openinghours.dto.OpeningHoursCreationCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
@AutoConfigureTestDatabase
public class InstitutionServiceTest {

   private InstitutionCreationCommand institutionCreationCommand;

    @Autowired
    private InstitutionService institutionService;

    @BeforeEach
    public void init() throws NotFoundException {
        institutionCreationCommand = getOneInstitutionCommand();
        institutionService.saveInstitution(institutionCreationCommand);

    }

    @Test
    public void testGetInstitutionDetailsById_shouldThrowException_givenId() {
        assertThrows(EntityNotFoundException.class, () -> institutionService.getInstitutionDetailsById(10l));
    }

    @Test
    public void testGetInstitutionDetailsById_shouldCreateObject_givenId() {
        Long id = institutionService.findByName(institutionCreationCommand.getName()).getId();

        InstitutionDetailsData institutionDetails = institutionService.getInstitutionDetailsById(id);

        assertNotNull(institutionDetails);
    }

    @Test
    public void testSaveInstitution_shouldReturnCreatedInstitution() {
        assertFalse(institutionService.findAllInstitutions().isEmpty());
    }

    @Test
    public void testFindByName_shouldReturnFoundInstitution() {
        Institution institution = institutionService.findByName("PecskeInstitution");

        assertNotNull(institution);
    }


    private InstitutionCreationCommand getOneInstitutionCommand() {
        String name = "PecskeInstitution";
        String email = "pecske92@gmail.com";
        Integer zipcode = 1089;
        String city = "Budapest";
        String address = "Orczy út 43";
        String phone = "+36205851886";
        String website = "";
        List<OpeningHoursCreationCommand> openingHours = new ArrayList<>();
        OpeningHoursCreationCommand openingHour = new OpeningHoursCreationCommand("Hétfő", LocalTime.now(), LocalTime.now());
        openingHours.add(openingHour);
        String description = "Rövid leírás ezúttal is elmarad érdeklődés és idő hiányából adódóan!";
        return new InstitutionBuilder()
                .setName(name)
                .setEmail(email)
                .setZipcode(zipcode)
                .setCity(city)
                .setAddress(address)
                .setPhone(phone)
                .setWebsite(website)
                .setOpeningHoursCreationCommands(openingHours)
                .setDescription(description)
                .buildInstitutionCreationCommand();
    }
}
