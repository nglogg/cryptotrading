
package com.cryptotrading.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TradeType {
    BUY("BUY"),
    SELL("SELL");

    @JsonCreator
    public static TradeType of(String value) {
        if (null == value) {
            return null;
        }

        for (TradeType item : TradeType.values()) {
            if (value.equalsIgnoreCase(item.getName())) {
                return item;
            }
        }

        throw new IllegalArgumentException("TradeType: unknown value: " + value);
    }

    private final String name;

    @JsonValue
    public String getName() {
        return name;
    }
    TradeType(String name) {
        this.name = name;
    }

}


