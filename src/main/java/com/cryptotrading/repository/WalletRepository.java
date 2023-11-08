package com.cryptotrading.repository;

import com.cryptotrading.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, String> {

    // Find the latest wallet balance for a given user
    Optional<Wallet> findFirstByUserIdOrderByTimestampDesc(String userId);
}

