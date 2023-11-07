package com.cryptotrading.repository;

import com.cryptotrading.model.BinancePrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface BinancePriceRepository extends JpaRepository<BinancePrice, BigDecimal> {
    // Custom database queries if necessary
}
