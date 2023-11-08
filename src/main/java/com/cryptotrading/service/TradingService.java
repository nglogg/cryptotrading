package com.cryptotrading.service;

import com.cryptotrading.mapper.TransactionMapper;
import com.cryptotrading.model.*;
import com.cryptotrading.repository.TransactionRepository;
import com.cryptotrading.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TradingService {

    private final TransactionMapper transactionMapper;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final CryptoPriceService priceService;

    @Transactional
    public Transaction executeTrade(TradeRequest tradeRequest, boolean isBuy) {
        // Perform validation checks
        // e.g., check if user has enough balance for the trade

        // Fetch the latest aggregated price
        Optional<CryptoPrice> price = priceService.findLatestBestPriceBySymbol(tradeRequest.getTradeType());
        if(price.isEmpty())
            return null;
        BigDecimal amount = BigDecimal.ZERO;
        if(isBuy){
            amount = price.get().getAskPrice();
        }
        else{
            amount = price.get().getBidPrice();
        }

        // Update the user's wallet balance
        User user = userRepository.findByGuid(tradeRequest.getUserId()).get();

        // Save the trade
        Transaction trade = new Transaction();
        trade = transactionMapper.toTransaction(tradeRequest, amount);
        BigDecimal finalAmount = amount;
        user.getWallets().stream().filter(w -> w.getType().getSymbol().equals(tradeRequest.getTradeType()))
                .forEach( wallet -> wallet.setBalance(wallet.getBalance().add(finalAmount)));

        // Update the wallet in the same transaction
        transactionRepository.save(trade);
        userRepository.save(user);
        // Return the completed trade
        return trade;
    }
}

