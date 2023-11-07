package com.cryptotrading.model.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;

@Data
public class PriceDto {
    @NotNull
    @Size(min = 3, max = 5)
    private String cryptocurrency;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal bidPrice; // Price for sell orders

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal askPrice; // Price for buy orders

    @NotNull
    private Instant timestamp;
}
