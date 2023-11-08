package com.cryptotrading.model;
public enum CryptoType {
    ETHEREUM("ETHUSDT"),
    BITCOIN("BTCUSDT");

    private final String symbol;

    CryptoType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}

