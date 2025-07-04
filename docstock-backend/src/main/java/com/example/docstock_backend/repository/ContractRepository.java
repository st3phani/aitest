package com.example.docstock_backend.repository;

import com.example.docstock_backend.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
}
