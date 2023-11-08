package com.cryptotrading.model;

import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TradeRequest {
    private String guid;
    private String userId;
    private String cryptocurrency;
    private BigDecimal quantity;
    private String tradeType;
}

