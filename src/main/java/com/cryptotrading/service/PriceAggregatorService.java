package com.cryptotrading.service;
import com.cryptotrading.model.BinancePrice;
import com.cryptotrading.model.HoubiPrice;
import com.cryptotrading.repository.BinancePriceRepository;
import com.cryptotrading.repository.HoubiPriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PriceAggregatorService {

    private final RestTemplate restTemplate;
    private final HoubiPriceRepository houbiPriceRepository;
    private final BinancePriceRepository binancePriceRepository;


    @Scheduled(fixedDelay = 10000)
    public void aggregatePrices() {
        CompletableFuture<BinancePrice> binancePriceFuture = fetchBinancePrice();
        CompletableFuture<HoubiPrice> huobiPriceFuture = fetchHuobiPrice();

        CompletableFuture.allOf(binancePriceFuture, huobiPriceFuture).join();

        try {
            Price binancePrice = binancePriceFuture.get();
            Price huobiPrice = huobiPriceFuture.get();

            // Here you would have logic to compare prices and store the best one.
            // For now, let's assume we just save them both.
            binancePriceRepository.save(binancePrice);
            houbiPriceRepository.save(huobiPrice);

        } catch (InterruptedException | ExecutionException e) {
            // Proper error handling should be here
            e.printStackTrace();
        }
    }

    public CompletableFuture<BinancePrice> fetchBinancePrice() {
        return CompletableFuture.supplyAsync(() -> {
            String binanceApiUrl = "https://api.binance.com/api/v3/ticker/bookTicker";
            // Fetch the data from Binance
            // For this example, we just create a dummy Price object
            BinancePrice price = restTemplate.getForObject(binanceApiUrl, BinancePrice.class);
            log.info("fetch Binance Price: {}", price);
            return price != null ? price : new BinancePrice();
        });
    }

    public CompletableFuture<HoubiPrice> fetchHuobiPrice() {
        return CompletableFuture.supplyAsync(() -> {
            String huobiApiUrl = "https://api.huobi.pro/market/tickers";
            // Fetch the data from Huobi
            // For this example, we just create a dummy Price object
            HoubiPrice price = restTemplate.getForObject(huobiApiUrl, HoubiPrice.class);
            log.info("fetch Houbi Price: {}", price);
            return price != null ? price : new HoubiPrice();
        });
    }
}

