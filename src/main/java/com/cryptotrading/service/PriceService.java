package com.cryptotrading.service;


import com.cryptotrading.model.CryptoPrice;
import com.cryptotrading.repository.CryptoPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceService {
    private final CryptoPriceRepository cryptoPriceRepository;

    public CryptoPrice getLastestPrice() {
        List<CryptoPrice> price = cryptoPriceRepository.findAll();
        return price.get(0);
    }
}
