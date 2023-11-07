package com.cryptotrading.service;

import com.cryptotrading.mapper.TransactionMapper;
import com.cryptotrading.model.*;
import com.cryptotrading.repository.TransactionRepository;
import com.cryptotrading.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TradingService {

    private final TransactionMapper transactionMapper;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final PriceService priceService;

    @Transactional
    public Transaction executeTrade(TradeRequest tradeRequest, boolean isBuy) {
        // Perform validation checks
        // e.g., check if user has enough balance for the trade

        // Fetch the latest aggregated price
        CryptoPrice price = priceService.getLastestPrice();
        BigDecimal amount = BigDecimal.ZERO;
        if(isBuy){
            amount = price.getAskPrice();
        }
        else{
            amount = price.getBidPrice();
        }

        // Update the user's wallet balance
        User user = userRepository.findById(tradeRequest.getUserId()).get();

        // Save the trade
        Transaction trade = new Transaction();
        trade = transactionMapper.toTransaction(tradeRequest, amount);
        user.setBtcBalance(user.getBtcBalance().add(amount));
        // Update the wallet in the same transaction
        transactionRepository.save(trade);
        userRepository.save(user);
        // Return the completed trade
        return trade;
    }
}

