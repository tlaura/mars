package com.progmasters.mars.account.service;

import com.progmasters.mars.account.ProviderAccountBuilder;
import com.progmasters.mars.account.domain.InstitutionType;
import com.progmasters.mars.account.domain.ProviderAccount;
import com.progmasters.mars.institution.dto.InstitutionListData;
import com.progmasters.mars.institution.service.InstitutionOpeningHoursService;
import com.progmasters.mars.institution.service.InstitutionService;
import com.progmasters.mars.mail.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@Rollback
@AutoConfigureTestDatabase
@ExtendWith(MockitoExtension.class)
public class AccountInstitutionServiceIT {

    private AccountInstitutionService accountInstitutionService;

    @Mock
    private AccountService accountServiceMock;

    @Mock
    private InstitutionOpeningHoursService institutionOpeningHoursServiceMock;

    @Mock
    private InstitutionService institutionServiceMock;

    @Mock
    private EmailService emailServiceMock;

    @BeforeEach
    public void init() {
        this.accountInstitutionService = new AccountInstitutionService(accountServiceMock, institutionServiceMock, institutionOpeningHoursServiceMock, emailServiceMock);
    }

    @Test
    public void testGetInstitutionsByAccountType_shouldReturnFalse() {
        List<InstitutionListData> institutionDetails = new ArrayList<>();
        institutionDetails.add(createOneInstitutionListData());
        List<ProviderAccount> accounts = new ArrayList<>();
        accounts.add(createOneProviderAccount());

        when(accountServiceMock.getAccountsByType(InstitutionType.DIAGNOSTIC_CENTER)).thenReturn(accounts);
        when(institutionServiceMock.getInstitutionsByProviderAccount(any(ProviderAccount.class))).thenReturn(institutionDetails);

        List<InstitutionListData> institutions = accountInstitutionService.getInstitutionsByAccountType(InstitutionType.DIAGNOSTIC_CENTER);

        assertFalse(institutions.isEmpty());

    }


    private ProviderAccount createOneProviderAccount() {

        List<InstitutionType> types = new ArrayList<>();
        types.add(InstitutionType.DIAGNOSTIC_CENTER);
        types.add(InstitutionType.THERAPY);

        return new ProviderAccountBuilder()
                .setProviderServiceName("PescskeTestService")
                .setName("Pecske")
                .setPassword("ValarMorghulis7")
                .setEmail("pecske92@gmail.com")
                .setInstitutionTypes(types)
                .setPhone("+36205851886")
                .setZipcode(1089)
                .setCity("Budapest")
                .setAddress("Orczy út 43")
                .setAgeGroupMin(10)
                .setAgeGroupMax(22)
                .setNewsletter(true)
                .buildProviderAccount();
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

        return new InstitutionListData(id, name, descritpion, email, website, phone, zipcode, city, address, longitude, latitude);
    }

}
