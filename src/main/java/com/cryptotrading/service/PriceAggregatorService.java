package com.cryptotrading.service;

import com.cryptotrading.parser.PriceParser;
import com.cryptotrading.model.CryptoPrice;
import com.cryptotrading.repository.CryptoPriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PriceAggregatorService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final CryptoPriceRepository cryptoPriceRepository;
    private final PriceParser priceParser;


    @Scheduled(fixedDelayString = "${app.aggregation.interval}")
    public void aggregatePrices() throws Exception {
        try {
            log.info("retrieve the pricing from the Binance and Houbi");
            // Fetch prices from Binance
            String binanceResponse = restTemplate.getForObject("https://api.binance.com/api/v3/ticker/bookTicker", String.class);
            List<CryptoPrice> binancePrice = priceParser.parseBinanceResponse(binanceResponse);
            log.info("Price were gotten from Binance: {}", binancePrice);
            // Fetch prices from Huobi
            String huobiResponse = restTemplate.getForObject("https://api.huobi.pro/market/tickers", String.class);
            List<CryptoPrice> huobiPrice = priceParser.parseHuobiResponse(huobiResponse);
            log.info("Price were gotten from Houbi: {}", huobiPrice);

            // Compare the prices and store the best in the database
            for(CryptoPrice b: binancePrice) {
                for( CryptoPrice h: huobiPrice){
                    priceParser.comparePrices(b, h).ifPresent(cryptoPriceRepository::save);
                }
            }

        } catch (IOException e) {
            // Handle your errors here
            throw new Exception("Exception happen while aggregating prices");
        }
    }

    // ... rest of the class
}

