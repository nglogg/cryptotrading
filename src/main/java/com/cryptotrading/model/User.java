package com.cryptotrading.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private BigDecimal usdtBalance = new BigDecimal("50000");
    private BigDecimal ethBalance = BigDecimal.ZERO;
    private BigDecimal btcBalance = BigDecimal.ZERO;

    // Getters and setters
}

