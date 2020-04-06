package com.progmasters.mars.account_institution.account.service;

import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import com.progmasters.mars.account_institution.account.domain.ProviderType;
import com.progmasters.mars.account_institution.account.domain.User;
import com.progmasters.mars.account_institution.account.dto.*;
import com.progmasters.mars.account_institution.connector.dto.AccountInstitutionListData;
import com.progmasters.mars.account_institution.institution.dto.InstitutionCreationCommand;
import com.progmasters.mars.chat.domain.LoginState;
import com.progmasters.mars.mail.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

@SpringBootTest
@Transactional
@Rollback
@AutoConfigureTestDatabase
public class AccountServiceTest {


    private ProviderAccount providerAccount;

    @Autowired
    private AccountService accountService;

    @Mock
    private EmailService emailService;

    @BeforeEach
    public void init() {
        ProviderAccountCreationCommand providerAccountCreationCommand = createOneAccountCommand();
        emailService = mock(EmailService.class);
        doNothing().when(emailService).sendConfirmationEmail(any(User.class));
        this.accountService.setEmailService(emailService);
        providerAccount = (ProviderAccount) accountService.save(providerAccountCreationCommand);
    }

    @Test
    public void testSaveProviderAccount_returnsCreatedId() {
        assertFalse(accountService.findAllAccounts().isEmpty());
    }

    @Test
    public void testDeleteProviderAccount_shouldThrowException_givenAccountEmail() {
        accountService.deleteUser("pecske92@gmail.com");
        assertThrows(EntityNotFoundException.class, () -> accountService.getProviderAccountByEmail("pecske92@gmail.com"));
    }

    @Test
    public void testRemoveById_shouldReturnTrue() {
        ProviderAccount account = (ProviderAccount) accountService.findByEmail("pecske92@gmail.com");
        accountService.removeByEmail(account.getEmail());

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
        List<ProviderAccount> accounts = accountService.getAccountsByType(ProviderType.DIAGNOSTIC_CENTER);

        assertFalse(accounts.isEmpty());
    }

    @Test
    public void testPasswordChange_shouldReturnTrue_givenCorrectNewPassword() {
        PasswordChangeDetails passwordChangeDetails = new PasswordChangeDetails("pecske92@gmail.com", "ValarMorghulis7", "NewValarMorghulis777", "NewValarMorghulis777");
        boolean isPasswordChanged = accountService.updatePasswordOfLoggedInUser(passwordChangeDetails);
        assertTrue(isPasswordChanged);
    }

    @Test
    public void testPasswordChange_shouldReturnFalse_givenIncorrectOldPassword() {
        PasswordChangeDetails passwordChangeDetails = new PasswordChangeDetails("pecske92@gmail.com", "ValarMorghul7", "NewValarMorghulis777", "NewValarMorghulis777");
        boolean isPasswordChanged = accountService.updatePasswordOfLoggedInUser(passwordChangeDetails);
        assertFalse(isPasswordChanged);
    }

    @Test
    public void testGetAccountByType_shoulBeNull_giveNotExistingType() {
        List<ProviderAccount> accounts = accountService.getAccountsByType(ProviderType.DAY_CARE);

        assertTrue(accounts.isEmpty());
    }

    @Test
    public void testFindById_shouldThrowException_giveNotExistingId() {
        assertThrows(EntityNotFoundException.class, () -> accountService.findById(2l));
    }

    @Test
    public void testUpdateProviderAccount() {
        ProviderUserDetails accountDetails = creatOneUserDetailsEdit();
        accountService.updateProviderAccountDetails(accountDetails, providerAccount.getId());

        assertEquals("PecskeTestService2", providerAccount.getProviderServiceName());
        assertEquals("Pecske2", providerAccount.getName());
        assertEquals("+36205851882", providerAccount.getPhone());
        assertEquals(1056, providerAccount.getZipcode());
        assertEquals("Budapest", providerAccount.getCity());
        assertEquals("Irányi utca 3", providerAccount.getAddress());
        assertEquals("Diagnózis központ", providerAccount.getTypes().get(0).getHungarianName());
        assertTrue(providerAccount.getNewsletter());
    }

    @Test
    public void testSetLoginState() {
        accountService.setLoginState("pecske92@gmail.com", LoginState.ONLINE);
        User foundUser = accountService.findByEmail("pecske92@gmail.com");

        assertSame(foundUser.getState(), LoginState.ONLINE);
    }

    @Test
    public void testUpdateUser() {
        UserDetailsData userDetailsData = createOneUserDetails();
        UserCreationCommand userCreationCommand = createOneUserCommand();
        accountService.save(userCreationCommand);
        accountService.updateUser(userDetailsData, "peter.erdei92@gmail.com");

        User found = accountService.findByEmail("peter.erdei92@gmail.com");

        assertEquals("Pecske2", found.getName());
        assertEquals("+36205851882", found.getPhone());
    }

    @Test
    public void testFindProvidersByAgeRange_shouldReturnNotEmptyList() {
        List<AccountInstitutionListData> ageRangeList = accountService.findProvidersByAgeRange(20);

        assertFalse(ageRangeList.isEmpty());
    }


    private ProviderUserDetails creatOneUserDetailsEdit() {
        String serviceName = "PecskeTestService2";
        String name = "Pecske2";
        String email = "pecske92@gmail.com";
        String phone = "+36205851882";
        Integer zipcode = 1056;
        String city = "Budapest";
        String address = "Irányi utca 3";
        List<String> types = new ArrayList<>();
        types.add("Diagnózis központ");
        Boolean newsletter = true;

        return ProviderUserDetails.builder()
                .providerServiceName(serviceName)
                .name(name)
                .email(email)
                .phone(phone)
                .zipcode(zipcode)
                .city(city)
                .address(address)
                .types(types)
                .newsletter(newsletter)
                .build();
    }

    private UserCreationCommand createOneUserCommand() {
        return new UserCreationCommand("Pecske", "Test123", "peter.erdei92@gmail.com",
                "+36205851886", 1089, "Budapest", "Orczy út 43", false);
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

        return ProviderAccountCreationCommand.builder()
                .providerServiceName(serviceName)
                .name(name)
                .password(pw)
                .email(email)
                .types(types)
                .phone(phone)
                .zipcode(zipcode)
                .city(city)
                .address(address)
                .ageGroupMin(ageGroupMin)
                .ageGroupMax(ageGroupMax)
                .institutions(institutions)
                .newsletter(newsletter)
                .build();
    }

    private UserDetailsData createOneUserDetails() {
        String name = "Pecske2";
        String phone = "+36205851882";
        Integer zipcode = 1089;
        String city = "Budapest";
        String address = "Orczy út 43";

        return UserDetailsData.builder()
                .name(name)
                .phone(phone)
                .zipcode(zipcode)
                .city(city)
                .address(address)
                .build();


    }


}
