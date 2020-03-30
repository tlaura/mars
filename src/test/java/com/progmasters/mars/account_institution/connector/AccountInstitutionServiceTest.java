package com.progmasters.mars.account_institution.connector;

import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import com.progmasters.mars.account_institution.account.domain.ProviderType;
import com.progmasters.mars.account_institution.account.service.AccountService;
import com.progmasters.mars.account_institution.institution.dto.InstitutionListData;
import com.progmasters.mars.account_institution.institution.location.GeocodeService;
import com.progmasters.mars.account_institution.institution.service.InstitutionOpeningHoursService;
import com.progmasters.mars.account_institution.institution.service.InstitutionService;
import com.progmasters.mars.mail.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback
@AutoConfigureTestDatabase
@ExtendWith(MockitoExtension.class)
public class AccountInstitutionServiceTest {

    private AccountInstitutionService accountInstitutionService;

    @Mock
    private AccountService accountServiceMock;

    @Mock
    private InstitutionOpeningHoursService institutionOpeningHoursServiceMock;

    @Mock
    private InstitutionService institutionServiceMock;

    @Mock
    private EmailService emailServiceMock;

    @Mock
    private AccountInstitutionConnectorRepository accountInstitutionConnectorRepository;

    @Mock
    private GeocodeService geocodeService;

    @BeforeEach
    public void init() {
        this.accountInstitutionService = new AccountInstitutionService(accountServiceMock, institutionServiceMock, institutionOpeningHoursServiceMock, emailServiceMock, accountInstitutionConnectorRepository, geocodeService);
    }

    //todo repair tests

//    @Test
//    public void testGetInstitutionsByAccountType_shouldReturnFalse() {
//        List<InstitutionListData> institutionDetails = new ArrayList<>();
//        institutionDetails.add(createOneInstitutionListData());
//        List<ProviderAccount> accounts = new ArrayList<>();
//        accounts.add(createOneProviderAccount());
//
//        when(accountServiceMock.getAccountsByType(ProviderType.DIAGNOSTIC_CENTER)).thenReturn(accounts);
//
//
//        //    when(institutionServiceMock.getInstitutionsByProviderAccount(any(ProviderAccount.class))).thenReturn(institutionDetails);
//
//        //  List<InstitutionListData> institutions = accountInstitutionService.getInstitutionsByAccountType(ProviderType.DIAGNOSTIC_CENTER);
//
//        //  assertFalse(institutions.isEmpty());
//
//    }


    private ProviderAccount createOneProviderAccount() {

        List<ProviderType> types = new ArrayList<>();
        types.add(ProviderType.DIAGNOSTIC_CENTER);
        types.add(ProviderType.THERAPY);

        return ProviderAccount.builder()
                .providerServiceName("PecskeTestService")
                .name("Pecske")
                .password("ValarMorghulis7")
                .email("pecske92@gmail.com")
                .types(types)
                .phone("+36205851886")
                .zipcode(1089)
                .city("Budapest")
                .address("Orczy út 43")
                .ageGroupMin(10)
                .ageGroupMax(22)
                .newsletter(true)
                .build();
    }

    private InstitutionListData createOneInstitutionListData() {
        Long id = 1l;
        String name = "Test";
        Integer zipcode = 1089;
        String city = "Budapest";
        String address = "Orczy út 43";
        String email = "pecske92@gmail.com";
        String descritpion = "nincs";
        Double longitude = 0d;
        Double latitude = 0d;
        String website = "none.com";
        String phone = "+3620585186";

        return InstitutionListData.builder()
                .id(id)
                .name(name)
                .zipcode(zipcode)
                .city(city)
                .address(address)
                .email(email)
                .description(descritpion)
                .longitude(longitude)
                .latitude(latitude)
                .website(website)
                .phone(phone)
                .build();
    }
}
