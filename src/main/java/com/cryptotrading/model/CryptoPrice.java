package com.cryptotrading.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "crypto_prices")
@AllArgsConstructor
public class CryptoPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String symbol;

    @Column(nullable = false)
    private BigDecimal bidPrice;

    @Column(nullable = false)
    private BigDecimal askPrice;

    @Column(name = "price_timestamp", nullable = false)
    private LocalDateTime timestamp;

    // Getters and setters
}

