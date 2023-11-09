package com.cryptotrading.controller;

import com.cryptotrading.exception.TradingException;
import com.cryptotrading.model.*;
import com.cryptotrading.service.TradingService;
import com.cryptotrading.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final TradingService tradingService;

    @GetMapping("/{userId}/wallets/balances")
    public ResponseEntity<Wallet> getWalletBalances(@PathVariable("userId") String userId) {
        return userService.getWalletBalances(userId, 0, 1)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{userId}/transactions")
    public ResponseEntity<List<Transaction>> getTransactions (@PathVariable("userId") String userId) {
        List<Transaction> transaction = userService.getTransactions(userId, 0, 10);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PostMapping(path="/trade", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<Transaction> executeTrade(@Valid TradeRequest tradeRequest) throws TradingException {
        User user = userService.mockUserDataWithTransactions();
        tradeRequest.setUserId(user.getGuid());
        Transaction transaction = tradingService.executeTrade(tradeRequest);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }
}