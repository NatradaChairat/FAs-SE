package camt.se.fas.dao;

import camt.se.fas.entity.Account;
import com.google.firebase.auth.FirebaseAuthException;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.swing.text.StyledEditorKit;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface AccountDao{
    String createAccount(Account account) throws FirebaseAuthException;
    Boolean addAccountInfo(Account account) throws ExecutionException, InterruptedException, FirebaseAuthException;
    Boolean saveReasonByUID(String reason, String uid) throws ExecutionException, InterruptedException;
    Boolean saveImage(Account account) throws ExecutionException, InterruptedException;

    Boolean changeAccountStatus(Account account) throws ExecutionException, InterruptedException, FirebaseAuthException;

    Account getAccountByUID(String uid) throws ExecutionException, InterruptedException;
    String getEmailByUID(String uid) throws FirebaseAuthException;
    String getPhonenumberByUID(String uid)throws FirebaseAuthException;

    Boolean findAccountByStudentId(String studentId) throws ExecutionException, InterruptedException;
    Boolean findAccountByphonenumber(String phonenumber) throws ExecutionException, InterruptedException;

    List<Account> getAccountByType(String type) throws ExecutionException, InterruptedException;
    List<Account> getAccountByStatus(String status) throws ExecutionException, InterruptedException;
    String getReasonByUID(String uid) throws ExecutionException, InterruptedException;
}
