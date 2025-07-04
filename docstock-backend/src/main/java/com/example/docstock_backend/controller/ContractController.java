package com.example.docstock_backend.controller;

import com.example.docstock_backend.dto.ContractRequest;
import com.example.docstock_backend.model.Beneficiary;
import com.example.docstock_backend.model.Contract;
import com.example.docstock_backend.service.ContractService;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequestMapping("/contracts")
@RequiredArgsConstructor
public class ContractController {
    private final ContractService contractService;

    @PostMapping
    public Contract create(@RequestBody ContractRequest request) {
        Beneficiary b = new Beneficiary();
        b.setName(request.getBeneficiaryName());
        b.setAddress(request.getBeneficiaryAddress());
        return contractService.createContract(request.getTemplate(), b);
    }

    @GetMapping
    public List<Contract> list() {
        return contractService.findAll();
    }

    @GetMapping("/{id}")
    public Contract get(@PathVariable Long id) {
        return contractService.getById(id);
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> exportPdf(@PathVariable Long id) throws DocumentException {
        Contract contract = contractService.getById(id);
        if (contract == null) {
            return ResponseEntity.notFound().build();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();
        document.add(new Paragraph(contract.getContent()));
        document.close();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=contract-" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(baos.toByteArray());
    }
}
