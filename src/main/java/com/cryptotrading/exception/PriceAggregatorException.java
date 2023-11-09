package com.cryptotrading.exception;

public class PriceAggregatorException extends RuntimeException {
    public PriceAggregatorException(String message) {
        super(message);
    }

    public PriceAggregatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
