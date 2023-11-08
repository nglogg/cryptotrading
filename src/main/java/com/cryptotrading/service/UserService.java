package com.cryptotrading.service;

import com.cryptotrading.model.CryptoPrice;
import com.cryptotrading.model.Transaction;
import com.cryptotrading.model.User;
import com.cryptotrading.model.Wallet;
import com.cryptotrading.repository.TransactionRepository;
import com.cryptotrading.repository.UserRepository;
import com.cryptotrading.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final WalletRepository walletRepository;

    private final TransactionRepository transactionRepository;
    public Optional<Wallet> getWalletBalances(String userId) {

        Page<Wallet> latestWallet = walletRepository.findFirstByUserIdOrderByTimestampDesc(userId,  PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "timestamp")));
        if (!latestWallet.hasContent()) {
            return Optional.empty();
        }
        Optional<Wallet> wallet = Optional.ofNullable(latestWallet.getContent().get(0));
        return wallet;
    }
    public Transaction getTransactions(Long userId) {
        Optional<Transaction> transactions = transactionRepository.findById(userId);
        return transactions.get();
    }
}