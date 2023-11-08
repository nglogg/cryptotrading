package com.cryptotrading.controller;

import com.cryptotrading.model.CryptoPrice;
import com.cryptotrading.model.CryptoType;
import com.cryptotrading.service.CryptoPriceService;
import com.cryptotrading.validator.CryptoTypeSubset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;

@RestController
@RequestMapping("/prices")
@Slf4j
public class CryptoPriceController {

    private final CryptoPriceService cryptoPriceService;

    @Autowired
    public CryptoPriceController(CryptoPriceService cryptoPriceService) {
        this.cryptoPriceService = cryptoPriceService;
    }

    @GetMapping("/latest")
    public ResponseEntity<CryptoPrice> getLatestPrice(@Valid @CryptoTypeSubset(anyOf = {CryptoType.ETHEREUM, CryptoType.BITCOIN}) @RequestParam("type") CryptoType type) {
        return cryptoPriceService.findLatestBestPriceBySymbol(type.getSymbol())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
