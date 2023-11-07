package com.cryptotrading.scheduler;

import com.cryptotrading.model.CryptoPrice;
import com.cryptotrading.repository.CryptoPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PriceAggregationScheduler {

    private final CryptoPriceRepository cryptoPriceRepository;
    @Scheduled(fixedRate = 10000)
    public void aggregatePrices() {
        // Code to call Binance and Huobi APIs, parse JSON responses, and find the best price
        // This is just a placeholder for actual implementation
        BigDecimal binanceBid = BigDecimal.TEN; // Fetch from Binance
        BigDecimal huobiBid = BigDecimal.TEN; // Fetch from Huobi

        BigDecimal bestBid = binanceBid.compareTo(huobiBid) > 0 ? binanceBid : huobiBid;

        // Similarly fetch and compare ask prices
        BigDecimal bestAsk = BigDecimal.TEN;

        // Update the price in the database (you need to inject a repository to do this)
        CryptoPrice cryptoPrice = new CryptoPrice();
        cryptoPrice.setCryptoPair("ETHUSDT");
        cryptoPrice.setBidPrice(bestBid);
        cryptoPrice.setAskPrice(bestAsk);
        cryptoPrice.setLastUpdated(LocalDateTime.now());

        //cryptoPriceRepository.save(cryptoPrice);
    }
}

