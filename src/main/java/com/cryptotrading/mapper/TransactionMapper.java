package com.cryptotrading.mapper;

import com.cryptotrading.model.TradeRequest;
import com.cryptotrading.model.Transaction;
import com.cryptotrading.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(source = "user", target = "user")
    @Mapping(source = "amount", target = "amount")
    Transaction toTransaction(TradeRequest request, User user, BigDecimal amount);
}
