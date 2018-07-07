package camt.se.fas.service;

import camt.se.fas.entity.Account;

import java.util.List;

public interface AccountService {
    Account addAccount(Account account);
    Account findById(long id);
    Account findByUsername(String username);
    String findAccountByUsername(String username);
    String findAccountByEmail(String email);
    List<Account> getAccount();
}
