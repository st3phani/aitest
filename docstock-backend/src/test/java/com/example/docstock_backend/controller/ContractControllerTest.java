package com.example.docstock_backend.controller;

import com.example.docstock_backend.dto.ContractRequest;
import com.example.docstock_backend.model.Beneficiary;
import com.example.docstock_backend.model.Contract;
import com.example.docstock_backend.service.ContractService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContractController.class)
class ContractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ContractService contractService;

    @Test
    void createContractEndpoint() throws Exception {
        ContractRequest req = new ContractRequest();
        req.setTemplate("T ${beneficiary} ${date}");
        req.setBeneficiaryName("Bob");
        req.setBeneficiaryAddress("addr");

        Contract contract = new Contract();
        contract.setId(1L);
        contract.setOrderNumber("no");
        contract.setIssueDate(LocalDate.now());
        contract.setContent("c");
        contract.setBeneficiary(new Beneficiary());

        when(contractService.createContract(ArgumentMatchers.anyString(), ArgumentMatchers.any(Beneficiary.class)))
                .thenReturn(contract);

        mockMvc.perform(post("/contracts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void listContractsEndpoint() throws Exception {
        Contract c = new Contract();
        c.setId(1L);
        when(contractService.findAll()).thenReturn(Collections.singletonList(c));

        mockMvc.perform(get("/contracts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}
