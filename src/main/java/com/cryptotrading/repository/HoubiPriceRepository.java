package com.cryptotrading.repository;

import com.cryptotrading.model.dto.HoubiPriceDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface HoubiPriceRepository extends JpaRepository<HoubiPriceDto, BigDecimal> {
    // Custom database queries if necessary
}
