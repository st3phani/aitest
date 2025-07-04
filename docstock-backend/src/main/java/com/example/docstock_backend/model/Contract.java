package com.example.docstock_backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNumber;

    private LocalDate issueDate;

    @Lob
    private String content;

    @ManyToOne(cascade = CascadeType.ALL)
    private Beneficiary beneficiary;
}
