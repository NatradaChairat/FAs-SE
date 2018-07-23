package camt.se.fas.service;

import camt.se.fas.entity.Account;

import java.util.List;

public interface AccountService {
    Account testDao();
    Account addAccountOfRegistrationStep1(Account account);
    Account updateAccountOfRegistrationStep2(Account account);
    Account updateStatus(Account account, String status);
    /*Boolean addAccountOfRegisterStepOne(Account account);
    String addUsernamePasswordToDB(String accountId, Account account);//
    String addStatusToDB(String accountId, String status);//
    String addEmailPhonenumberToDB(String accountId,Account account);//*/
    Account findAccountByUsername(String username);//
    Account findAccountByEmail(String email);//
    Account findAccountByStudentId(String studentId);
    Account findAccountByPhonenumber(String phonenumber);
    //Boolean updateStatusByEmail(String email, String status);
    List<Account> getAccounts();
    Account getAccount(String accountId);
}
