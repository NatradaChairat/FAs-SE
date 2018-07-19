package camt.se.fas.dao;

import camt.se.fas.entity.Account;

public interface AccountDao{
    String addUsernamePassword(Account account);
    String addStatus(Account account,String status);//
    String addEmailPhonenumber(Account account);//
    Account findAccountByEmail(String email);
    Account findAccountByUsername(String username);
    Account  findLastAccountId();

}
