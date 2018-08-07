package camt.se.fas.service;

import camt.se.fas.dao.AccountDao;
import camt.se.fas.entity.Account;
import com.google.firebase.auth.FirebaseAuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class AccountServiceImpl implements AccountService {
    Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);
    @Autowired
    AccountDao accountDao;

    @Override
    public String registerAccount(Account account) throws FirebaseAuthException, ExecutionException, InterruptedException {
        String uid = accountDao.createAccount(account);
        LOGGER.info("UID "+uid);
        account.setUid(uid);
        boolean result = accountDao.addStatus(account);
        if(result) {
            return uid;
        }else return null;
    }

    @Override
    public Boolean registerAccountInfo(Account account) throws ExecutionException, InterruptedException, FirebaseAuthException {
        return accountDao.addAccountInfo(account);
    }


    @Override
    public Boolean checkDuplicatedStudentId(String studentId) throws ExecutionException, InterruptedException {
        String result = accountDao.findDocIdByStudentId(studentId);
        if(result==null) {
            return false;
        }else return true;
    }

    @Override
    public Boolean checkDuplicatedPhonenumber(String phonenumber) throws ExecutionException, InterruptedException {
        String result = accountDao.findDocIdByphonenumber(phonenumber);
        if(result==null) {
            return false;
        }else return true;
    }

    @Override
    public String getEmailByUID(String uid) throws FirebaseAuthException {
        return accountDao.findEmailByUID(uid);
    }

    @Override
    public String getPhonenumberByUID(String uid) throws FirebaseAuthException {
        return accountDao.findPhonenumberByUID(uid);
    }

    @Override
    public Boolean updateStatus(Account account, String status) throws ExecutionException, InterruptedException, FirebaseAuthException {
        return accountDao.updateStatus(account, status);
    }
}
