package com.example.service;

import com.example.entity.Account;

import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // 1
    public Account addNewUser(Account account) {
        Optional<Account> accountOptional = accountRepository.findAccountByUsername(account.getUsername());

        if (accountOptional.isPresent()) {
            account.setAccountId(409);
            return account;
        } else if (account.getUsername().length() == 0 && account.getPassword().length() < 4)
            return null;
        else
            return accountRepository.save(account);
    }

    // 2
    public Account userLogin(Account account) {
        Optional<Account> accountOptional = accountRepository.findAccountByUsernameAndPassword(account.getUsername(),
                account.getPassword());

        return accountOptional.orElse(null);
    }

}
