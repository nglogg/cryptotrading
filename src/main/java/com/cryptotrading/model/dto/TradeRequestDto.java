package com.cryptotrading.model.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
@Data
public class TradeRequestDto {
    @NotNull
    private Long userId;

    @NotNull
    @Size(min = 3, max = 5)
    private String cryptocurrency;

    @NotNull
    @Positive
    private BigDecimal quantity;

    @NotNull
    @Size(min = 3, max = 4)
    private String tradeType; // "BUY" or "SELL"
}

