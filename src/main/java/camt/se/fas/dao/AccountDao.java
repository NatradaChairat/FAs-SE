package camt.se.fas.dao;

import camt.se.fas.entity.Account;

import java.util.List;

public interface AccountDao {
    Account addAccount(Account account);
    Account findById(long id);
    Account findByUsername(String username);
    Account findByEmail(String email);
    List<Account> getAccount();
}