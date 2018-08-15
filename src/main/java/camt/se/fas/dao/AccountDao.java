package camt.se.fas.dao;

import camt.se.fas.entity.Account;
import com.google.firebase.auth.FirebaseAuthException;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.concurrent.ExecutionException;

public interface AccountDao{
    String createAccount(Account account) throws FirebaseAuthException;
    Boolean addAccountInfo(Account account) throws ExecutionException, InterruptedException, FirebaseAuthException;

    Boolean changeAccountStatus(Account account) throws ExecutionException, InterruptedException, FirebaseAuthException;
    Boolean addStatus(Account account) throws ExecutionException, InterruptedException;
    Boolean updateStatus(Account account, String status) throws ExecutionException, InterruptedException, FirebaseAuthException;

    Boolean findAccountByAccountId(String accountId);
    String findEmailByUID(String uid) throws FirebaseAuthException;
    String findPhonenumberByUID(String uid)throws FirebaseAuthException;
    String findDocIdByStudentId(String studentId) throws ExecutionException, InterruptedException;
    String findDocIdByphonenumber(String phonenumber) throws ExecutionException, InterruptedException;

    //Account updateStatusByAccountId(String accountId, String status);

}
