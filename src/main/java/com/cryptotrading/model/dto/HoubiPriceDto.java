package com.cryptotrading.model.dto;

import com.cryptotrading.model.HoubiPrice;
import lombok.Data;

import java.util.ArrayList;

@Data
public class HoubiPriceDto {
    public ArrayList<HoubiPrice> data;
}
