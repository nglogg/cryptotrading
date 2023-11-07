package com.cryptotrading.mapper;

import com.cryptotrading.model.TradeRequest;
import com.cryptotrading.model.dto.TradeRequestDto;
import org.mapstruct.Mapper;

@Mapper
public interface TradeRequestMapper {

    TradeRequest toTradeRequest(TradeRequestDto request);
}
