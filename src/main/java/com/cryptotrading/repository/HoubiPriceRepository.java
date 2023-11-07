package com.cryptotrading.repository;

import com.cryptotrading.model.HoubiPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface HoubiPriceRepository extends JpaRepository<HoubiPrice, BigDecimal> {
    // Custom database queries if necessary
}
