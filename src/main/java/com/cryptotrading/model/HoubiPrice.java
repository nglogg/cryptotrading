package com.cryptotrading.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HoubiPrice {
    public String symbol;
    public double bid;
    public double ask;
}
