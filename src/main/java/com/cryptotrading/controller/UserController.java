package com.cryptotrading.controller;

import com.cryptotrading.model.Transaction;
import com.cryptotrading.model.User;
import com.cryptotrading.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}/wallet/balances")
    public ResponseEntity<User> getWalletBalances(@PathVariable("userId") String userId) {
        User user = userService.getWalletBalances(Long.valueOf(userId));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{userId}/transactions ")
    public ResponseEntity<Transaction> getTransactions (@PathVariable("userId") String userId) {
        Transaction transaction = userService.getTransactions(Long.valueOf(userId));
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
}