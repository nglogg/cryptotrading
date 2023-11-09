package com.cryptotrading.service;

import com.cryptotrading.config.ConfigProperties;
import com.cryptotrading.exception.PriceAggregatorException;
import com.cryptotrading.model.CryptoPrice;
import com.cryptotrading.parser.PriceParser;
import com.cryptotrading.repository.CryptoPriceRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PriceAggregatorService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final CryptoPriceRepository cryptoPriceRepository;
    private final PriceParser priceParser;
    private final ConfigProperties configProperties;

    @Scheduled(fixedDelayString = "${app.aggregation.interval}")
    @SchedulerLock(name = "AggregatePrices", lockAtMostFor = "PT15S", lockAtLeastFor = "PT10S")
    public void aggregatePrices() {
        try {
            log.info("retrieve the pricing from the Binance and Huobi");
            // Fetch prices from Binance
            List<CryptoPrice> binancePrice = getPricesFromBinance();
            log.info("Price were gotten from Binance: {}", binancePrice);
            // Fetch prices from Huobi
            List<CryptoPrice> huobiPrice = getPricesFromHuobi();
            log.info("Price were gotten from Huobi: {}", huobiPrice);

            compareAndStorePrices(binancePrice, huobiPrice);

        } catch (JsonProcessingException | RestClientException e) {
            throw new PriceAggregatorException("Error fetching prices from API", e);
        }
    }

    @Cacheable(value = "binancePrices", unless = "#result == null")
    private List<CryptoPrice> getPricesFromBinance() throws RestClientException, JsonProcessingException {
        String binanceResponse = restTemplate.getForObject(configProperties.getBinanceUrl(), String.class);
        return priceParser.parseBinanceResponse(binanceResponse);
    }

    @Cacheable(value = "huobiPrices", unless = "#result == null")
    private List<CryptoPrice> getPricesFromHuobi() throws RestClientException, JsonProcessingException {
        String huobiResponse = restTemplate.getForObject(configProperties.getHoubiUrl(), String.class);
        return priceParser.parseHuobiResponse(huobiResponse);
    }

    private void compareAndStorePrices(List<CryptoPrice> binancePrice, List<CryptoPrice> huobiPrice) {
        for(CryptoPrice b: binancePrice) {
            for(CryptoPrice h: huobiPrice){
                priceParser.comparePrices(b, h).ifPresent(cryptoPriceRepository::save);
            }
        }
    }
}


