package com.progmasters.mars.account_institution.connector;

import com.progmasters.mars.account_institution.institution.dto.InstitutionCreationCommand;
import com.progmasters.mars.account_institution.institution.openinghours.dto.OpeningHoursCreationCommand;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback
@AutoConfigureTestDatabase
public class AccountInstitutionServiceTest {

    @Autowired
    private AccountInstitutionService accountInstitutionService;

    @BeforeEach
    public void init() {
        InstitutionCreationCommand institutionCreationCommand = buildOneInstitutionCommand();

    }

    private InstitutionCreationCommand buildOneInstitutionCommand() {
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
        return InstitutionCreationCommand.builder()
                .name(name)
                .email(email)
                .zipcode(zipcode)
                .city(city)
                .address(address)
                .phone(phone)
                .website(website)
                .openingHours(openingHours)
                .description(description)
                .build();
    }

}
