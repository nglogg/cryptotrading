package com.cryptotrading.service;

import com.cryptotrading.model.Transaction;
import com.cryptotrading.model.User;
import com.cryptotrading.model.Wallet;
import com.cryptotrading.repository.TransactionRepository;
import com.cryptotrading.repository.UserRepository;
import com.cryptotrading.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final WalletRepository walletRepository;

    private final TransactionRepository transactionRepository;
    public Wallet getWalletBalances(String userId) {
        Optional<Wallet> user = walletRepository.findFirstByUserIdOrderByTimestampDesc(userId);
        return user.get();
    }
    public Transaction getTransactions(Long userId) {
        Optional<Transaction> transactions = transactionRepository.findById(userId);
        return transactions.get();
    }
}