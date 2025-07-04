package com.example.docstock_backend.repository;

import com.example.docstock_backend.model.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {
}
