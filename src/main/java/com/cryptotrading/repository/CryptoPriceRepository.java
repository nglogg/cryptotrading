package com.cryptotrading.repository;

import com.cryptotrading.model.CryptoPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface CryptoPriceRepository extends JpaRepository<CryptoPrice, BigDecimal> {
    // Custom database queries if necessary
}
