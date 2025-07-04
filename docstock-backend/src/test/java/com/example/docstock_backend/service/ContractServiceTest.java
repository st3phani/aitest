package com.example.docstock_backend.service;

import com.example.docstock_backend.model.Beneficiary;
import com.example.docstock_backend.model.Contract;
import com.example.docstock_backend.repository.ContractRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContractServiceTest {

    @Mock
    private ContractRepository contractRepository;

    @InjectMocks
    private ContractService contractService;

    @Test
    void createContractGeneratesFieldsAndSaves() {
        Beneficiary b = new Beneficiary();
        b.setName("Alice");
        b.setAddress("addr");
        String template = "Hello ${beneficiary} on ${date}";

        when(contractRepository.save(any(Contract.class))).thenAnswer(invocation -> {
            Contract c = invocation.getArgument(0);
            c.setId(1L);
            return c;
        });

        Contract result = contractService.createContract(template, b);

        assertNotNull(result.getOrderNumber());
        assertEquals(LocalDate.now(), result.getIssueDate());
        assertEquals("Hello Alice on " + LocalDate.now(), result.getContent());
        verify(contractRepository).save(any(Contract.class));
    }
}
