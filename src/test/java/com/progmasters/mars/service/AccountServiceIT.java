package com.progmasters.mars.service;

import com.progmasters.mars.domain.InstitutionType;
import com.progmasters.mars.domain.ProviderAccount;
import com.progmasters.mars.dto.InstitutionCreationCommand;
import com.progmasters.mars.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.dto.ProviderUserDetails;
import com.progmasters.mars.dto.ProviderUserDetailsEdit;
import com.progmasters.mars.util.ProviderAccountBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
@AutoConfigureTestDatabase
public class AccountServiceIT {

    private ProviderAccountCreationCommand providerAccount;

    @Autowired
    private AccountService accountService;

    @BeforeEach
    public void init() {
        this.providerAccount = createOneAccountCommand();
        accountService.save(providerAccount);
    }


    @Test
    public void testSaveProviderAccount_returnsCreatedId() {

        assertFalse(accountService.findAllAccounts().isEmpty());
    }

    @Test
    public void testRemoveById_shouldReturnTrue() {
        ProviderAccount account = accountService.findByEmail("pecske92@gmail.com");
        accountService.removeById(account.getId());

        assertTrue(accountService.findAllAccounts().isEmpty());
    }

    @Test
    public void testGetProviderAccountByEmail_shoulReturnProviderUserDetails_givenExistingEmail() {

        ProviderUserDetails foundAccount = accountService.getProviderAccountByEmail("pecske92@gmail.com");

        assertNotNull(foundAccount);
    }

    @Test
    public void testGetProviderAccountByEmail_shouldThrowException_givenNotExistingEmail() {

        assertThrows(EntityNotFoundException.class, () -> accountService.getProviderAccountByEmail("none"));
    }

    @Test
    public void testGetAccountByType_shouldNotNull_giveExistingType() {
        List<ProviderAccount> accounts = accountService.getAccountsByType(InstitutionType.DIAGNOSTIC_CENTER);

        assertFalse(accounts.isEmpty());
    }

    @Test
    public void testGetAccountByType_shoulBeNull_giveNotExistingType() {
        List<ProviderAccount> accounts = accountService.getAccountsByType(InstitutionType.DAY_CARE);

        assertTrue(accounts.isEmpty());
    }

    @Test
    public void testFindById_shouldThrowException_giveNotExistingId() {
        assertThrows(EntityNotFoundException.class, () -> accountService.findById(2l));
    }


    @Test
    public void testGetProviderAccountEditDetailsByEmail_shouldNotBeNull_givenValidEmail() {
        ProviderUserDetailsEdit foundAccountDetails = accountService.getProviderAccountEditDetailsByEmail("pecske92@gmail.com");

        assertNotNull(foundAccountDetails);
    }

    @Test
    public void testGetProviderAccountsEditDetailsByEmail_shouldThrowException_givenNotValidEmail() {

        assertThrows(EntityNotFoundException.class, () -> accountService.getProviderAccountEditDetailsByEmail("none"));
    }

    @Test
    public void testUpdateProviderAccount() {
        ProviderUserDetailsEdit accountDetails = creatOneUserDetailsEdit();
        ProviderUserDetailsEdit editedAccount = accountService.updateProviderAccount(accountDetails, "pecske92@gmail.com");

        assertEquals("PecskeTestService2", editedAccount.getProviderServiceName());
        assertEquals("Pecske2", editedAccount.getName());
        assertEquals("+36205851882", editedAccount.getPhone());
        assertEquals(1056, editedAccount.getZipcode());
        assertEquals("Budapest", editedAccount.getCity());
        assertEquals("Irányi utca 3", editedAccount.getAddress());
        assertTrue(editedAccount.getNewsletter());
    }

    private ProviderUserDetailsEdit creatOneUserDetailsEdit() {
        String serviceName = "PecskeTestService2";
        String name = "Pecske2";
        String pw = "ValarMorghulis7";
        String email = "pecske92@gmail.com";
        String phone = "+36205851882";
        Integer zipcode = 1056;
        String city = "Budapest";
        String address = "Irányi utca 3";
        Boolean newsletter = true;

        return new ProviderAccountBuilder().setProviderServiceName(serviceName)
                .setName(name)
                .setPassword(pw)
                .setEmail(email)
                .setPhone(phone)
                .setZipcode(zipcode)
                .setCity(city)
                .setAddress(address)
                .setNewsletter(newsletter)
                .buildDetailsEdit();
    }

    private ProviderAccountCreationCommand createOneAccountCommand() {
        String serviceName = "PecskeTestService";
        String name = "Pecske";
        String pw = "ValarMorghulis7";
        String email = "pecske92@gmail.com";
        List<String> types = new ArrayList<>();
        types.add("Diagnózis központ");
        types.add("Terápia");
        String phone = "+36205851886";
        Integer zipcode = 1089;
        String city = "Budapest";
        String address = "Orczy út 43";
        Integer ageGroupMin = 10;
        Integer ageGroupMax = 22;
        List<InstitutionCreationCommand> institutions = new ArrayList<>();
        Boolean newsletter = false;

        return new ProviderAccountBuilder()
                .setProviderServiceName(serviceName)
                .setName(name)
                .setPassword(pw)
                .setEmail(email)
                .setTypes(types)
                .setPhone(phone)
                .setZipcode(zipcode)
                .setCity(city)
                .setAddress(address)
                .setAgeGroupMax(ageGroupMax)
                .setAgeGroupMin(ageGroupMin)
                .setInstitutions(institutions)
                .setNewsletter(newsletter)
                .buildCreationCommand();
    }
}
