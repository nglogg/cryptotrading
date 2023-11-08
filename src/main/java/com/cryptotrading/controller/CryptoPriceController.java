package com.cryptotrading.controller;

import com.cryptotrading.model.CryptoPrice;
import com.cryptotrading.service.CryptoPriceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/prices")
public class CryptoPriceController {

    private final CryptoPriceService cryptoPriceService;

    @Autowired
    public CryptoPriceController(CryptoPriceService cryptoPriceService) {
        this.cryptoPriceService = cryptoPriceService;
    }

    @GetMapping("/latest")
    public ResponseEntity<CryptoPrice> getLatestPrice(@RequestParam String symbol) {
        return cryptoPriceService.findLatestBestPriceBySymbol(symbol)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
