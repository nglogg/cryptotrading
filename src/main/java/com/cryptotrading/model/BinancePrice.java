package com.cryptotrading.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BinancePrice {
    public String symbol;
    public double bidPrice;
    public double askPrice;
}
