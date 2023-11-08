package com.cryptotrading.repository;

import com.cryptotrading.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Custom database queries if necessary

    @Query("SELECT t FROM Transaction as t join t.user as u WHERE u.guid = :userId ORDER BY t.timestamp DESC")
    Page<Transaction> findFirstByUserIdOrderByTimestampDesc(@Param("userId") String userId, Pageable pageable);
}

