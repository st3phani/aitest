package com.example.docstock_backend.dto;

import lombok.Data;

@Data
public class ContractRequest {
    private String template;
    private String beneficiaryName;
    private String beneficiaryAddress;
}
