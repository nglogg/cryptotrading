package com.cryptotrading.controller;

import com.cryptotrading.mapper.TradeRequestMapper;
import com.cryptotrading.model.Transaction;
import com.cryptotrading.model.dto.TradeRequestDto;
import com.cryptotrading.service.TradingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trade")
@RequiredArgsConstructor
public class TradingController {

    private final TradingService tradingService;

    private final TradeRequestMapper tradeRequestMapper;

    @PostMapping("/buy")
    public ResponseEntity<Transaction> buyCrypto(@RequestBody TradeRequestDto tradeRequestDto) {
        Transaction transaction = tradingService.executeTrade(tradeRequestMapper.toTradeRequest(tradeRequestDto), true);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @PostMapping("/sell")
    public ResponseEntity<Transaction> sellCrypto(@RequestBody TradeRequestDto tradeRequestDto) {
        Transaction transaction = tradingService.executeTrade(tradeRequestMapper.toTradeRequest(tradeRequestDto), false);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }
}

