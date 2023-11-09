package com.cryptotrading.parser;

import com.cryptotrading.exception.PriceAggregatorException;
import com.cryptotrading.model.BinancePrice;
import com.cryptotrading.model.CryptoPrice;
import com.cryptotrading.model.CryptoType;
import com.cryptotrading.model.dto.HoubiPriceDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class PriceParser {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<CryptoPrice> parseBinanceResponse(String jsonResponse) throws PriceAggregatorException, JsonProcessingException {
        BinancePrice[] binancePriceDto = objectMapper.readValue(jsonResponse, BinancePrice[].class);
        List<CryptoPrice> bestPrice = Arrays.stream(binancePriceDto)
                .filter(datum -> CryptoType.BITCOIN.getSymbol().equalsIgnoreCase(datum.getSymbol()) || CryptoType.ETHEREUM.getSymbol().equalsIgnoreCase(datum.getSymbol()))
                .map( b -> new CryptoPrice(
                        null,
                        b.getSymbol().toUpperCase(),
                        b.getBidPrice(),
                        b.getAskPrice(),
                        LocalDateTime.now()))
                .collect(Collectors.toList());
        if (bestPrice.isEmpty()) {
            throw new PriceAggregatorException("No ETHUSDT or BTCUSDT price data available from Huobi");
        }
        return bestPrice;
    }

    public List<CryptoPrice> parseHuobiResponse(String jsonResponse) throws PriceAggregatorException, JsonProcessingException {
        HoubiPriceDto huobiPriceDto = objectMapper.readValue(jsonResponse, HoubiPriceDto.class);
        List<CryptoPrice> bestPrice = huobiPriceDto.getData().stream()
                .filter(datum -> CryptoType.BITCOIN.getSymbol().equalsIgnoreCase(datum.getSymbol()) || CryptoType.ETHEREUM.getSymbol().equalsIgnoreCase(datum.getSymbol()))
                .map( b -> new CryptoPrice(
                null,
                b.getSymbol().toUpperCase(),
                b.getBid(),
                b.getAsk(),
                LocalDateTime.now()))
                .collect(Collectors.toList());

        if (bestPrice.isEmpty()) {
            throw new PriceAggregatorException("No ETHUSDT or BTCUSDT price data available from Huobi");
        }
        return bestPrice;
    }

    public Optional<CryptoPrice> comparePrices(CryptoPrice binancePrice, CryptoPrice huobiPrice) {
        if(binancePrice.getSymbol().equals(huobiPrice.getSymbol())){
            // Compare bid price for SELL order, choose the higher one as it is more beneficial for seller
            double bestBid = Math.max(binancePrice.getBidPrice(), huobiPrice.getBidPrice());

            // Compare ask price for BUY order, choose the lower one as it is more beneficial for buyer
            double bestAsk = Math.min(binancePrice.getAskPrice(), huobiPrice.getAskPrice());

            return Optional.of(new CryptoPrice(
                    null,
                    binancePrice.getSymbol(),
                    bestBid,
                    bestAsk,
                    LocalDateTime.now()));
        }
        return Optional.empty();
    }
}
