package com.cryptotrading.controller;

import com.cryptotrading.model.CryptoPrice;
import com.cryptotrading.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prices")
@RequiredArgsConstructor
public class PriceController {
    private final PriceService priceService;

    @GetMapping("/latest")
    public ResponseEntity<CryptoPrice> getLastestPrice() {
        CryptoPrice price = priceService.getLastestPrice();
        return new ResponseEntity<>(price, HttpStatus.OK);
    }
}
