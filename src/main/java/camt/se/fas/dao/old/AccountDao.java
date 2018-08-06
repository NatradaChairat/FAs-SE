package camt.se.fas.dao.old;

import camt.se.fas.entity.Account;

public interface AccountDao {
    Account addUsernamePasswordStudentId(Account account);
    Account addStatus(Account account,String status);//
    Account addEmailPhonenumber(Account account);//
    Account addDOBFirstnameLastname(Account account);
    Account findAccountByEmail(String email);
    Account findAccountByUsername(String username);
    Account findAccountByStudentId(String studentId);
    Account findAccountByPhonenumber(String phonenumber);
    Account findLastAccountId();
    Account findAccountByAccountId(String accountId);
}
