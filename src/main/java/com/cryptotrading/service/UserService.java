package com.cryptotrading.service;

import com.cryptotrading.model.Transaction;
import com.cryptotrading.model.User;
import com.cryptotrading.repository.TransactionRepository;
import com.cryptotrading.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final TransactionRepository transactionRepository;
    public User getWalletBalances(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.get();
    }
    public Transaction getTransactions(Long userId) {
        Optional<Transaction> transactions = transactionRepository.findById(userId);
        return transactions.get();
    }
}