package com.cryptotrading.repository;

import com.cryptotrading.model.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    // Find the latest wallet balance for a given user
    @Query("SELECT wa FROM Wallet as wa join wa.user as u WHERE u.guid = :userId ORDER BY wa.timestamp DESC")
    Page<Wallet> findFirstByUserIdOrderByTimestampDesc(@Param("userId") String userId, Pageable pageable);
}

