package com.cryptotrading.repository;

import com.cryptotrading.model.User;
import com.cryptotrading.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Custom database queries if necessary
    Optional<User> findByGuid(String guid);
}
