package com.cryptotrading.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class CryptoPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cryptoPair;
    private BigDecimal bidPrice;
    private BigDecimal askPrice;
    private LocalDateTime lastUpdated;

    // Getters and setters
}

