package com.progmasters.mars.account_institution.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.progmasters.mars.account_institution.connector.controller.AccountInstitutionController;
import com.progmasters.mars.account_institution.connector.dto.AccountInstitutionListData;
import com.progmasters.mars.account_institution.institution.domain.Institution;
import com.progmasters.mars.account_institution.institution.dto.InstitutionCreationCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@Rollback
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
public class AccountInstitutionControllerTest {

    @Autowired
    private AccountInstitutionController accountInstitutionController;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {

    }

    @Test
    public void getAllListItemsEmpty() throws Exception {

        MvcResult result =
                mockMvc.perform(get("/api/connectors"))
                        .andExpect(status().isOk())
                        .andReturn();

// this uses a TypeReference to inform Jackson about the Lists's generic type
        List<AccountInstitutionListData> actual = objectMapper
                .readValue(result
                                .getResponse()
                                .getContentAsString(),
                        new TypeReference<List<AccountInstitutionListData>>() {
                        });

        assertEquals(0, actual.size());
    }

    @Test
    public void getAllListItemsInstitution() throws Exception {
        InstitutionCreationCommand institutionCreationCommand =
                InstitutionCreationCommand.builder()
                        .address("Május 1 út 12.")
                        .city("Kazincbarcika")
                        .zipcode(3700)
                        .description("Short description for test institution and for integration test.")
                        .email("test@test.hu")
                        .name("Test Institution")
                        .openingHours(new ArrayList<>())
                        .phone("+36303334444")
                        .build();

        accountInstitutionController
                .getAccountInstitutionService()
                .getInstitutionService()
                .saveInstitution(new Institution(institutionCreationCommand));

        MvcResult result = mockMvc.perform(get("/api/connectors"))
                .andExpect(request().asyncStarted())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk());


//
//        MvcResult result =
//                mockMvc.perform(get("/api/connectors"))
//                        .andExpect(request().asyncStarted())
//                        .andReturn();
//
//        mockMvc.perform(asyncDispatch(result))
//                .andExpect(status().isOk());

// this uses a TypeReference to inform Jackson about the Lists's generic type
//        List<AccountInstitutionListData> actual = objectMapper
//                .readValue(result
//                                .getResponse()
//                                .getContentAsString(),
//                        new TypeReference<List<AccountInstitutionListData>>() {
//                        });
//        assertEquals(1, actual.size());
    }
}
