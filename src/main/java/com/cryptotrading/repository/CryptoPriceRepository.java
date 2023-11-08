package com.cryptotrading.repository;
import org.springframework.data.jpa.repository.Query;
import com.cryptotrading.model.CryptoPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CryptoPriceRepository extends JpaRepository<CryptoPrice, Long> {
    // Find the latest price for a given cryptocurrency symbol
    @Query("SELECT cp FROM CryptoPrice cp WHERE cp.symbol = :symbol ORDER BY cp.timestamp DESC")
    Page<CryptoPrice> findLatestBySymbol(@Param("symbol") String symbol, Pageable pageable);

}
