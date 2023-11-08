package com.cryptotrading.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "crypto_prices")
@AllArgsConstructor
@NoArgsConstructor
public class CryptoPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String symbol;

    @Column(nullable = false)
    private double bidPrice;

    @Column(nullable = false)
    private double askPrice;

    @Column(name = "price_timestamp", nullable = false)
    private LocalDateTime timestamp;

    // Getters and setters
}

