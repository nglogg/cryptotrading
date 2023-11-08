package com.cryptotrading.service;

import com.cryptotrading.model.*;
import com.cryptotrading.repository.TransactionRepository;
import com.cryptotrading.repository.UserRepository;
import com.cryptotrading.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final WalletRepository walletRepository;

    private final UserRepository userRepository;

    private final TransactionRepository transactionRepository;
    public Optional<Wallet> getWalletBalances(String userId) {
        User mockUser = mockUserData();
        Page<Wallet> latestWallet = walletRepository.findFirstByUserIdOrderByTimestampDesc(mockUser.getGuid(),  PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "timestamp")));
        if (!latestWallet.hasContent()) {
            return Optional.empty();
        }
        Optional<Wallet> wallet = Optional.ofNullable(latestWallet.getContent().get(0));
        return wallet;
    }

    private User mockUserData() {
        Wallet mockWallet = new Wallet();
        mockWallet.setType(CryptoType.BITCOIN);
        User mockUser = new User(UUID.randomUUID().toString(),"testUser", Set.of(mockWallet), Collections.emptyList());
        mockWallet.setUser(mockUser);
        userRepository.save(mockUser);

        return mockUser;
    }
    private User mockUserDataWithTransactions() {
        Wallet mockWallet = new Wallet();
        mockWallet.setType(CryptoType.BITCOIN);
        User mockUser = new User(UUID.randomUUID().toString(),"testUser", Set.of(mockWallet), Collections.emptyList());
        mockWallet.setUser(mockUser);
        Transaction mockTransaction1 = new Transaction();
        mockTransaction1.setUser(mockUser);
        mockTransaction1.setType(CryptoType.BITCOIN);
        mockTransaction1.setAmount(BigDecimal.ONE);
        Transaction mockTransaction2 = new Transaction();
        mockTransaction2.setUser(mockUser);
        mockTransaction2.setType(CryptoType.BITCOIN);
        mockTransaction2.setAmount(BigDecimal.TEN);
        mockUser.setTransactions(List.of(mockTransaction1, mockTransaction2));
        userRepository.save(mockUser);

        return mockUser;
    }

    public List<Transaction> getTransactions(String userId) {
        User mockUser = mockUserDataWithTransactions();

        Page<Transaction> latestTransactions = transactionRepository.findFirstByUserIdOrderByTimestampDesc(mockUser.getGuid(),  PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "timestamp")));
        if (!latestTransactions.hasContent()) {
            return Collections.emptyList();
        }
        List<Transaction> transactions = latestTransactions.getContent();
        return transactions;
    }
}