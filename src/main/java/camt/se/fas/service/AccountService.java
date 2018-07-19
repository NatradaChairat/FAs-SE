package camt.se.fas.service;

import camt.se.fas.entity.Account;

import java.util.List;

public interface AccountService {
    Account testDao();
    Account addOnlyAccount(Account account);
    /*Boolean addAccountOfRegisterStepOne(Account account);
    String addUsernamePasswordToDB(String accountId, Account account);//
    String addStatusToDB(String accountId, String status);//
    String addEmailPhonenumberToDB(String accountId,Account account);//*/
    Account findAccountByUsername(String username);//
    Account findAccountByEmail(String email);//
    Boolean updateStatusByEmail(String email, String status);
    List<Account> getAccount();
}
