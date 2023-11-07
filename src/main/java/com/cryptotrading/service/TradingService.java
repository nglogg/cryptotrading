package com.cryptotrading.service;

import com.cryptotrading.mapper.TransactionMapper;
import com.cryptotrading.model.*;
import com.cryptotrading.repository.BinancePriceRepository;
import com.cryptotrading.repository.HoubiPriceRepository;
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
    private final BinancePriceRepository binancePriceRepository;
    private final HoubiPriceRepository houbiPriceRepository;

    @Transactional
    public Transaction executeTrade(TradeRequest tradeRequest, boolean isBuy) {
        // Perform validation checks
        // e.g., check if user has enough balance for the trade

        // Fetch the latest aggregated price
        Optional<BinancePrice> latestBinancePrice = binancePriceRepository.findById(BigDecimal.ONE);
        Optional<HoubiPrice> latestHoubiPrice = houbiPriceRepository.findById(BigDecimal.ONE);
        BigDecimal price = BigDecimal.ZERO;
        if(tradeRequest.getCryptocurrency() =="BINANCE"){
            if(isBuy){
                price = BigDecimal.valueOf(Long.parseLong(latestBinancePrice.get().getAskPrice()));
            }
            else{
                price = BigDecimal.valueOf(Long.parseLong(latestBinancePrice.get().getBidPrice()));
            }
        }
        else{
            if(isBuy){
                price = BigDecimal.valueOf(Long.parseLong(String.valueOf(latestHoubiPrice.get().getData().get(0).ask)));
            }
            else{
                price = BigDecimal.valueOf(latestHoubiPrice.get().getData().get(0).bid);
            }
        }
        BigDecimal amount = price.multiply(tradeRequest.getQuantity());
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

