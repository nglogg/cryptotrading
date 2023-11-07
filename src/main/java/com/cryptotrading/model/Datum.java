package com.cryptotrading.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Datum {
    public String symbol;
    @JsonProperty("open")
    public double myopen;
    public double high;
    public double low;
    public double close;
    public double amount;
    public double vol;
    public int count;
    public double bid;
    public double bidSize;
    public double ask;
    public double askSize;
}
