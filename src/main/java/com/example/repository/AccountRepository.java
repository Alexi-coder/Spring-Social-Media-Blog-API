package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findAccountByAccountId(Integer id);

    Optional<Account> findAccountByUsername(String username);

    Optional<Account> findAccountByUsernameAndPassword(String username, String password);
}
