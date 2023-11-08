package com.cryptotrading.model;

import lombok.Data;

@Data
public class HoubiPrice {
    public String symbol;
    public double bid;
    public double ask;
}
