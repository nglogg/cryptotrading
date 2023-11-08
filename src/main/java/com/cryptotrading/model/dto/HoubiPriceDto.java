package com.cryptotrading.model.dto;

import com.cryptotrading.model.HoubiPrice;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HoubiPriceDto {
    public ArrayList<HoubiPrice> data;
}
