package com.progmasters.mars.chat.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.progmasters.mars.account_institution.account.domain.User;
import com.progmasters.mars.account_institution.account.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.account_institution.account.dto.UserCreationCommand;
import com.progmasters.mars.chat.dto.ContactCreationCommand;
import com.progmasters.mars.chat.dto.ContactsData;
import com.progmasters.mars.chat.dto.MessageData;
import com.progmasters.mars.mail.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EmailService emailService;

    ContactCreationCommand contactCreationCommand;

    @BeforeEach
    public void init() {
        emailService = mock(EmailService.class);
        doNothing().when(emailService).sendConfirmationEmail(any(User.class));
        contactController.getChatService().getAccountService().setEmailService(emailService);
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
                        .ageGroupMin(0)
                        .ageGroupMax(99)
                        .providerServiceName("PecskeTestService")
                        .types(new ArrayList<>())
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
                        .ageGroupMin(0)
                        .ageGroupMax(99)
                        .providerServiceName("ElzaTestService")
                        .types(new ArrayList<>())
                        .build();
        contactController.getChatService().getAccountService().save(pecskeTest);
        contactController.getChatService().getAccountService().save(elzaTest);
        contactCreationCommand =
                new ContactCreationCommand("pecske92@gmail.com", "hudak.elza@gmail.com");

    }

    @Test
    public void saveContactTest() throws Exception {
        mockMvc.perform(post("/api/contacts")
                .content(objectMapper.writeValueAsString(contactCreationCommand))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void saveContactTestCantSaveMultipleContacts() throws Exception {
        mockMvc.perform(post("/api/contacts")
                .content(objectMapper.writeValueAsString(contactCreationCommand))
                .contentType(MediaType.APPLICATION_JSON));
        mockMvc.perform(post("/api/contacts")
                .content(objectMapper.writeValueAsString(contactCreationCommand))
                .contentType(MediaType.APPLICATION_JSON));

        String testEmail = "pecske92@gmail.com";

        MvcResult result =
                mockMvc.perform(get("/api/contacts")
                        .param("email", testEmail))
                        .andExpect(status().isOk())
                        .andReturn();

// this uses a TypeReference to inform Jackson about the Lists's generic type
        List<ContactsData> actual = objectMapper
                .readValue(result
                                .getResponse()
                                .getContentAsString(),
                        new TypeReference<List<ContactsData>>() {
                        });

        assertEquals(1, actual.size());

    }

    @Test
    public void getChatHistoryTestEmpty() throws Exception {
        mockMvc.perform(post("/api/contacts")
                .content(objectMapper.writeValueAsString(contactCreationCommand))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String pecskeEmail = "pecske92@gmail.com";
        String elzaEmail = "hudak.elza@gmail.com";

        MvcResult result =
                mockMvc.perform(get("/api/contacts/history")
                        .param("fromEmail", pecskeEmail)
                        .param("toEmail", elzaEmail))
                        .andExpect(status().isOk())
                        .andReturn();

// this uses a TypeReference to inform Jackson about the Lists's generic type
        List<MessageData> actual = objectMapper
                .readValue(result
                                .getResponse()
                                .getContentAsString(),
                        new TypeReference<List<MessageData>>() {
                        });

        assertEquals(0, actual.size());
    }

}
