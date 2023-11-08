package com.cryptotrading.parser;
import com.cryptotrading.model.*;
import com.cryptotrading.model.dto.BinancePriceDto;
import com.cryptotrading.model.dto.HoubiPriceDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class PriceParser {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CryptoPrice parseBinanceResponse(String jsonResponse) throws IOException {
        BinancePriceDto binancePriceDto = objectMapper.readValue(jsonResponse, BinancePriceDto.class);
        Optional<BinancePrice> bestPriceDatum = binancePriceDto.getData().stream()
                .filter(datum -> CryptoType.BITCOIN.getSymbol().equals(datum.getSymbol()) || CryptoType.ETHEREUM.getSymbol().equals(datum.getSymbol()))
                .findFirst();
        if (bestPriceDatum.isPresent()) {
            BinancePrice datum = bestPriceDatum.get();
            return new CryptoPrice(
                    null,
                    datum.getSymbol().toUpperCase(),
                    BigDecimal.valueOf(Long.parseLong(datum.getBidPrice())),
                    BigDecimal.valueOf(Long.parseLong(datum.getAskPrice())),
                    LocalDateTime.now());
        } else {
            throw new IllegalStateException("No ETHUSDT or BTCUSDT price data available from Huobi");
        }
    }

    public CryptoPrice parseHuobiResponse(String jsonResponse) throws IOException {
        HoubiPriceDto huobiPriceDto = objectMapper.readValue(jsonResponse, HoubiPriceDto.class);
        Optional<HoubiPrice> bestPriceDatum = huobiPriceDto.getData().stream()
                .filter(datum -> CryptoType.BITCOIN.getSymbol().equals(datum.getSymbol()) || CryptoType.ETHEREUM.getSymbol().equals(datum.getSymbol()))
                .findFirst();  // Assuming you are interested in the first matching symbol

        if (bestPriceDatum.isPresent()) {
            HoubiPrice datum = bestPriceDatum.get();
            return new CryptoPrice(
                    null,
                    datum.getSymbol().toUpperCase(),
                    BigDecimal.valueOf(datum.getBid()),
                    BigDecimal.valueOf(datum.getAsk()),
                    LocalDateTime.now());
        } else {
            throw new IllegalStateException("No ETHUSDT or BTCUSDT price data available from Huobi");
        }
    }

    public CryptoPrice comparePrices(CryptoPrice binancePrice, CryptoPrice huobiPrice) {
        // Compare bid price for SELL order, choose the higher one as it is more beneficial for seller
        BigDecimal bestBid = binancePrice.getBidPrice().compareTo(huobiPrice.getBidPrice()) > 0 ?
                binancePrice.getBidPrice() : huobiPrice.getBidPrice();

        // Compare ask price for BUY order, choose the lower one as it is more beneficial for buyer
        BigDecimal bestAsk = binancePrice.getAskPrice().compareTo(huobiPrice.getAskPrice()) < 0 ?
                binancePrice.getAskPrice() : huobiPrice.getAskPrice();

        return new CryptoPrice(
                null,
                bestBid.equals(binancePrice.getBidPrice()) ? binancePrice.getSymbol() : huobiPrice.getSymbol(),
                bestBid,
                bestAsk,
                LocalDateTime.now());
    }
}
