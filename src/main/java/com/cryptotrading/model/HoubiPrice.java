package com.cryptotrading.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class HoubiPrice {
    public ArrayList<Datum> data;
    public String status;
    public long ts;
}
