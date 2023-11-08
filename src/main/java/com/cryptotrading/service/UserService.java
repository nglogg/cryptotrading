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

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

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
        User mockUser = new User(UUID.randomUUID().toString(),"testUser", Set.of(mockWallet));
        mockWallet.setUser(mockUser);
        userRepository.save(mockUser);

        return mockUser;
    }

    public Transaction getTransactions(Long userId) {
        Optional<Transaction> transactions = transactionRepository.findById(userId);
        return transactions.get();
    }
}