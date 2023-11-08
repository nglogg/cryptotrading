package com.cryptotrading.service;

import com.cryptotrading.parser.PriceParser;
import com.cryptotrading.model.CryptoPrice;
import com.cryptotrading.repository.CryptoPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PriceAggregatorService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final CryptoPriceRepository cryptoPriceRepository;
    private final PriceParser priceParser;


    @Scheduled(fixedDelayString = "${app.aggregation.interval}")
    public void aggregatePrices() {
        try {
            // Fetch prices from Binance
            String binanceResponse = restTemplate.getForObject("https://api.binance.com/api/v3/ticker/bookTicker", String.class);
            CryptoPrice binancePrice = priceParser.parseBinanceResponse(binanceResponse);

            // Fetch prices from Huobi
            String huobiResponse = restTemplate.getForObject("https://api.huobi.pro/market/tickers", String.class);
            CryptoPrice huobiPrice = priceParser.parseHuobiResponse(huobiResponse);

            // Compare the prices and store the best in the database
            CryptoPrice bestPrice = priceParser.comparePrices(binancePrice, huobiPrice);

            cryptoPriceRepository.save(bestPrice);
        } catch (IOException e) {
            // Handle your errors here
            e.printStackTrace();
        }
    }

    // ... rest of the class
}

