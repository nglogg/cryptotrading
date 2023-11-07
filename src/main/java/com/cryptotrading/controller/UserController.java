package com.cryptotrading.controller;

import com.cryptotrading.mapper.TradeRequestMapper;
import com.cryptotrading.model.Transaction;
import com.cryptotrading.model.User;
import com.cryptotrading.model.dto.TradeRequestDto;
import com.cryptotrading.service.TradingService;
import com.cryptotrading.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final TradingService tradingService;

    private final TradeRequestMapper tradeRequestMapper;

    @GetMapping("/{userId}/wallet/balances")
    public ResponseEntity<User> getWalletBalances(@PathVariable("userId") String userId) {
        User user = userService.getWalletBalances(Long.valueOf(userId));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{userId}/transactions")
    public ResponseEntity<Transaction> getTransactions (@PathVariable("userId") String userId) {
        Transaction transaction = userService.getTransactions(Long.valueOf(userId));
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PostMapping("/{userId}/trade")
    public ResponseEntity<Transaction> executeTrade(@RequestBody TradeRequestDto tradeRequestDto) {
        Transaction transaction = tradingService.executeTrade(tradeRequestMapper.toTradeRequest(tradeRequestDto), true);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }
}