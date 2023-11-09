package com.cryptotrading.service;

import com.cryptotrading.mapper.TransactionMapper;
import com.cryptotrading.model.*;
import com.cryptotrading.repository.TransactionRepository;
import com.cryptotrading.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.cryptotrading.exception.TradingException;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradingService {

    private final TransactionMapper transactionMapper;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final CryptoPriceService priceService;

    @Transactional
    public Transaction executeTrade(TradeRequest tradeRequest) throws TradingException {

        // Fetch the latest aggregated price
        Optional<CryptoPrice> price = priceService.findLatestBestPriceBySymbol(tradeRequest.getType().getSymbol());
        if(price.isEmpty())
            return null;
        // Perform validation checks
        // e.g., check if user has enough balance for the trade
        Optional<User> user = userRepository.findByGuid(tradeRequest.getUserId());
        if(user.isEmpty())
            return null;
        Wallet wallet = user.get().getWallets().stream().filter( w -> w.getType().equals(tradeRequest.getType()))
                .findFirst()
                .orElseThrow( () ->
                        new TradingException("User don't have a wallet with type "+tradeRequest.getType().getSymbol()+" for trading")
                );
        double oldBalance = wallet.getBalance().doubleValue();
        double newBalance = getBalanceAfterTrading(tradeRequest, oldBalance, price.get());
        log.info("New transaction is created. Updated balance is "+newBalance);
        // Save the trade
        Transaction newTransaction = transactionMapper.toTransaction(tradeRequest, user.get(), BigDecimal.valueOf((Math.abs(newBalance -oldBalance))));
        wallet.setBalance( BigDecimal.valueOf(newBalance));
        userRepository.save(user.get());
        return transactionRepository.save(newTransaction);
    }

    private static double getBalanceAfterTrading(TradeRequest tradeRequest, double oldBalance, CryptoPrice price) throws TradingException {
        double balance = oldBalance;
        double amount = tradeRequest.getQuantity();
        if(tradeRequest.getTradeType().equals(TradeType.BUY)){
            log.info("Buy Price is "+price.getAskPrice());
            amount *= price.getAskPrice();
            if(balance < amount){
                throw new TradingException("User don't have enough balance for trading. Balance is "+balance);
            }
            amount *=-1;
        }
        else{
            log.info("Sell Price is "+price.getBidPrice());
            amount *= price.getBidPrice();
        }
        balance += amount;
        return balance;
    }
}

