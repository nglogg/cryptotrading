package com.cryptotrading.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "wallets", indexes = {
        @Index(name = "idx_user_id_currency", columnList = "user_id, currency")
})
@Data
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "guid")
    private User user;

    @Column(nullable = false)
    private BigDecimal balance = new BigDecimal("50000");

    @Column(nullable = false, length = 10)
    private CryptoType type;

}