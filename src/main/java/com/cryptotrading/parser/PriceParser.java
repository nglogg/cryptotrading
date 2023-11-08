package com.cryptotrading.parser;
import com.cryptotrading.model.*;
import com.cryptotrading.model.dto.HoubiPriceDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class PriceParser {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<CryptoPrice> parseBinanceResponse(String jsonResponse) throws IOException {
        BinancePrice[] binancePriceDto = objectMapper.readValue(jsonResponse, BinancePrice[].class);
        List<CryptoPrice> bestPrice = Arrays.stream(binancePriceDto)
                .filter(datum -> CryptoType.BITCOIN.getSymbol().equalsIgnoreCase(datum.getSymbol()) || CryptoType.ETHEREUM.getSymbol().equalsIgnoreCase(datum.getSymbol()))
                .map( b -> new CryptoPrice(
                        null,
                        b.getSymbol().toUpperCase(),
                        BigDecimal.valueOf(b.getBidPrice()),
                        BigDecimal.valueOf(b.getAskPrice()),
                        LocalDateTime.now()))
                .collect(Collectors.toList());
        if (bestPrice.isEmpty()) {
            throw new IllegalStateException("No ETHUSDT or BTCUSDT price data available from Huobi");
        }
        return bestPrice;
    }

    public List<CryptoPrice> parseHuobiResponse(String jsonResponse) throws IOException {
        HoubiPriceDto huobiPriceDto = objectMapper.readValue(jsonResponse, HoubiPriceDto.class);
        List<CryptoPrice> bestPrice = huobiPriceDto.getData().stream()
                .filter(datum -> CryptoType.BITCOIN.getSymbol().equalsIgnoreCase(datum.getSymbol()) || CryptoType.ETHEREUM.getSymbol().equalsIgnoreCase(datum.getSymbol()))
                .map( b -> new CryptoPrice(
                null,
                b.getSymbol().toUpperCase(),
                BigDecimal.valueOf(b.getBid()),
                BigDecimal.valueOf(b.getAsk()),
                LocalDateTime.now()))
                .collect(Collectors.toList());

        if (bestPrice.isEmpty()) {
            throw new IllegalStateException("No ETHUSDT or BTCUSDT price data available from Huobi");
        }
        return bestPrice;
    }

    public Optional<CryptoPrice> comparePrices(CryptoPrice binancePrice, CryptoPrice huobiPrice) {
        if(binancePrice.getSymbol().equals(huobiPrice.getSymbol())){
            // Compare bid price for SELL order, choose the higher one as it is more beneficial for seller
            BigDecimal bestBid = binancePrice.getBidPrice().compareTo(huobiPrice.getBidPrice()) > 0 ?
                    binancePrice.getBidPrice() : huobiPrice.getBidPrice();

            // Compare ask price for BUY order, choose the lower one as it is more beneficial for buyer
            BigDecimal bestAsk = binancePrice.getAskPrice().compareTo(huobiPrice.getAskPrice()) < 0 ?
                    binancePrice.getAskPrice() : huobiPrice.getAskPrice();

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
