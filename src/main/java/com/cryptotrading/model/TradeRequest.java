package com.cryptotrading.model;

import lombok.Data;

@Data
public class TradeRequest {
    private String id;
    private String userId;
    private CryptoType type;
    private double quantity;
    private TradeType tradeType;
}

