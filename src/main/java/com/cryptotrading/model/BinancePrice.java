package com.cryptotrading.model;

import lombok.Data;

@Data
public class BinancePrice {
    public String symbol;
    public String bidPrice;
    public String askPrice;
}
