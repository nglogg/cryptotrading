package com.cryptotrading.repository;

import com.cryptotrading.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Custom database queries if necessary
}

