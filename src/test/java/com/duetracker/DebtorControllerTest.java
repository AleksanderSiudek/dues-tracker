package com.duetracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DebtorControllerTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void returnDebtorsWithBalance() throws Exception {
        mockMvc.perform(get("/debtors")).andExpect(status().isOk()).andExpect(jsonPath("$[0].fullName").value("Anna Nowak")).andExpect(jsonPath("$[0].balance").value(-40.00));
    }
}