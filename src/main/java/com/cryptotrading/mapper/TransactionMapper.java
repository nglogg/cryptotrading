package com.cryptotrading.mapper;

import com.cryptotrading.model.TradeRequest;
import com.cryptotrading.model.Transaction;
import org.mapstruct.Mapper;

import java.math.BigDecimal;

@Mapper
public interface TransactionMapper {
    Transaction toTransaction(TradeRequest request, BigDecimal amount);
}
