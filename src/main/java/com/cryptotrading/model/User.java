package com.cryptotrading.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
@Table(name = "users", indexes = {
        @Index(name = "idx_guid", columnList = "guid"),
})
public class User {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(updatable = false, nullable = false)
    private String guid;

    private String username;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Wallet> wallets;
}

