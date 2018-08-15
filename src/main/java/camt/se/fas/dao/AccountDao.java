package camt.se.fas.dao;

import camt.se.fas.entity.Account;
import com.google.firebase.auth.FirebaseAuthException;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface AccountDao{
    String createAccount(Account account) throws FirebaseAuthException;
    Boolean addAccountInfo(Account account) throws ExecutionException, InterruptedException, FirebaseAuthException;

    Boolean changeAccountStatus(Account account) throws ExecutionException, InterruptedException, FirebaseAuthException;
    Boolean addStatus(Account account) throws ExecutionException, InterruptedException;
    Boolean updateStatus(Account account, String status) throws ExecutionException, InterruptedException, FirebaseAuthException;

    Boolean findAccountByAccountId(String accountId);
    String getEmailByUID(String uid) throws FirebaseAuthException;
    String getPhonenumberByUID(String uid)throws FirebaseAuthException;
    Boolean findAccountByStudentId(String studentId) throws ExecutionException, InterruptedException;
    Boolean findAccountByphonenumber(String phonenumber) throws ExecutionException, InterruptedException;

    List<Account> getAccountByStatus(String status) throws ExecutionException, InterruptedException;

    //Account updateStatusByAccountId(String accountId, String status);

}
