package com.cryptotrading.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wallets", indexes = {
        @Index(name = "idx_user_id_currency", columnList = "user_id"),
        @Index(name = "idx__wallet_timestamp", columnList = "timestamp")
})
@Getter
@Setter
public class Wallet{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "guid")
    @JsonBackReference
    private User user;

    @Column(nullable = false)
    private BigDecimal balance = new BigDecimal("50000");

    @Column(nullable = false, length = 10)
    private CryptoType type;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime timestamp;

}