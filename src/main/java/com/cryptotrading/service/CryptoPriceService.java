package com.cryptotrading.service;


import com.cryptotrading.model.CryptoPrice;
import com.cryptotrading.repository.CryptoPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CryptoPriceService {

    private final CryptoPriceRepository cryptoPriceRepository;

    @Autowired
    public CryptoPriceService(CryptoPriceRepository cryptoPriceRepository) {
        this.cryptoPriceRepository = cryptoPriceRepository;
    }

    public Optional<CryptoPrice> findLatestBestPriceBySymbol(String symbol) {
        Page<CryptoPrice> latestPricePage = cryptoPriceRepository.findLatestBySymbol(symbol, PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "timestamp")));

        if (!latestPricePage.hasContent()) {
            return Optional.empty();
        }

        Optional<CryptoPrice> latestPrice = Optional.ofNullable(latestPricePage.getContent().get(0));
        return latestPrice;
    }
}
