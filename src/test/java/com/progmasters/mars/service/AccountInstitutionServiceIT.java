package com.progmasters.mars.service;

import com.progmasters.mars.domain.InstitutionType;
import com.progmasters.mars.domain.ProviderAccount;
import com.progmasters.mars.dto.InstitutionListData;
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
        ProviderAccount providerAccount = new ProviderAccount();
        providerAccount.setId(1l);
        providerAccount.setProviderServiceName("PecskeTestService");
        providerAccount.setName("Pecske");
        providerAccount.setPassword("ValarMorghulis7");
        providerAccount.setEmail("pecske92@gmail.com");
        List<InstitutionType> types = new ArrayList<>();
        types.add(InstitutionType.DIAGNOSTIC_CENTER);
        types.add(InstitutionType.THERAPY);
        providerAccount.setTypes(types);
        providerAccount.setPhone("+36205851886");
        providerAccount.setZipcode(1089);
        providerAccount.setCity("Budapest");
        providerAccount.setAddress("Orczy út 43");
        providerAccount.setAgeGroupMin(10);
        providerAccount.setAgeGroupMax(22);
        providerAccount.setNewsletter(true);

        return providerAccount;
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
