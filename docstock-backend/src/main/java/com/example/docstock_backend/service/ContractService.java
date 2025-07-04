package com.example.docstock_backend.service;

import com.example.docstock_backend.model.Beneficiary;
import com.example.docstock_backend.model.Contract;
import com.example.docstock_backend.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;

    public Contract createContract(String template, Beneficiary beneficiary) {
        Contract contract = new Contract();
        contract.setOrderNumber(generateOrderNumber());
        contract.setIssueDate(LocalDate.now());

        String content = template
                .replace("${beneficiary}", beneficiary.getName())
                .replace("${date}", contract.getIssueDate().toString());
        contract.setContent(content);
        contract.setBeneficiary(beneficiary);

        return contractRepository.save(contract);
    }

    public List<Contract> findAll() {
        return contractRepository.findAll();
    }

    public Contract getById(Long id) {
        return contractRepository.findById(id).orElse(null);
    }

    private String generateOrderNumber() {
        return UUID.randomUUID().toString();
    }
}
