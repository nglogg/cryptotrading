package com.cryptotrading.model.dto;

import com.cryptotrading.model.BinancePrice;
import lombok.Data;

import java.util.ArrayList;
@Data
public class BinancePriceDto {
    public ArrayList<BinancePrice> data;
}
