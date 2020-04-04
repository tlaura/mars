package com.progmasters.mars.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progmasters.mars.account_institution.account.domain.User;
import com.progmasters.mars.account_institution.account.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.account_institution.account.dto.UserCreationCommand;
import com.progmasters.mars.account_institution.account.service.AccountService;
import com.progmasters.mars.chat.dto.ContactCreationCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@Rollback
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
public class ContactControllerTest {

    @Autowired
    private ContactController contactController;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private AccountService accountService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        accountService = Mockito.mock(AccountService.class);
        contactController.getChatService().setAccountService(accountService);
        UserCreationCommand pecskeTest =
                ProviderAccountCreationCommand.builder()
                        .address("Orczy út 43")
                        .city("Budapest")
                        .zipcode(1089)
                        .email("pecske92@gmail.com")
                        .name("Pecske")
                        .newsletter(false)
                        .password("ValarMorghulis7")
                        .phone("+36205851886")
                        .build();
        UserCreationCommand elzaTest =
                ProviderAccountCreationCommand.builder()
                        .address("Csontváry K T u 19")
                        .city("Budapest")
                        .zipcode(1181)
                        .email("hudak.elza@gmail.com")
                        .name("Elza")
                        .newsletter(false)
                        .password("ValarMorghulis7")
                        .phone("+36205851886")
                        .build();
        doReturn(new User(pecskeTest)).when(accountService).findByEmail("pecske92@gmail.com");
        doReturn(new User(elzaTest)).when(accountService).findByEmail("hudak.elza@gmail.com");
    }

    @Test
    public void saveContactTest() throws Exception {
        ContactCreationCommand contactCreationCommand =
                new ContactCreationCommand("pecske92@gmail.com", "hudak.elza@gmail.com");

        mockMvc.perform(post("/api/contacts")
                .content(objectMapper.writeValueAsString(contactCreationCommand))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

}
