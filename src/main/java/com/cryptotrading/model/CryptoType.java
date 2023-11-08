package com.cryptotrading.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum CryptoType {
    ETHEREUM("ETHUSDT"),
    BITCOIN("BTCUSDT");

    @JsonCreator
    public static CryptoType of(String value) {
        if (null == value) {
            return null;
        }

        for (CryptoType item : CryptoType.values()) {
            if (value.equalsIgnoreCase(item.getSymbol())) {
                return item;
            }
        }

        throw new IllegalArgumentException("CryptoType: unknown value: " + value);
    }

    private final String symbol;

    @JsonValue
    public String getSymbol() {
        return symbol;
    }
    CryptoType(String symbol) {
        this.symbol = symbol;
    }

}

