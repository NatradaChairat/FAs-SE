package camt.se.fas.service.old;

import camt.se.fas.entity.Account;

import java.util.List;

public interface AccountService {
    /*Account testDao();*/
    Account addAccountOfRegistrationStep1(Account account);
    Account updateAccountOfRegistrationStep2(Account account);//no unit test
    Account updateStatus(Account account, String status);
    Account findAccountByUsername(String username);
    Account findAccountByEmail(String email);
    Account findAccountByStudentId(String studentId);
    Account findAccountByPhonenumber(String phonenumber);
    List<Account> getAccounts();
    Account getAccount(String accountId);
}
