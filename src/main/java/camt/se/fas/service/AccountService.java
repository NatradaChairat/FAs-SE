package camt.se.fas.service;

import camt.se.fas.entity.Account;

import java.util.List;

public interface AccountService {
    Boolean addAccountOfRegisterStepOne(Account account);
    Boolean addUsernamePasswordToDB(String accountId, Account account);
    Boolean addStatusToDB(String accountId, String status);
    Boolean addEmailPhonenumberToDB(String accountId,Account account);
    String findAccountByUsername(String username);
    String findAccountByEmail(String email);
    Boolean updateStatusByEmail(String email, String status);
    List<Account> getAccount();
}
