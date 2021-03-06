package camt.se.fas.service;

import camt.se.fas.dao.AccountDao;
import camt.se.fas.entity.Account;
import com.google.firebase.auth.FirebaseAuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        account.setStatus("registered");
        boolean result = accountDao.changeAccountStatus(account);
        if(result) {
            return uid;
        }else return null;
    }

    @Override
    public Boolean registerAccountInfo(Account account) throws ExecutionException, InterruptedException, FirebaseAuthException {
        return accountDao.addAccountInfo(account);
    }

    @Override
    public Boolean checkIsStaff(String uid) throws ExecutionException, InterruptedException {
        List<Account> staffAccounts = accountDao.getAccountByType("staff");
        for(int i = 0 ; i<= (staffAccounts.size()-1) ;i++){
            String checker =staffAccounts.get(i).getUid();
            if(uid.equals(checker)){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }


    @Override
    public Boolean checkDuplicatedStudentId(String studentId) throws ExecutionException, InterruptedException {
        boolean result = accountDao.findAccountByStudentId(studentId);
        if(result) {
            return true;
        }else return false;
    }

    @Override
    public Boolean checkDuplicatedPhonenumber(String phonenumber) throws ExecutionException, InterruptedException {
        boolean result = accountDao.findAccountByphonenumber(phonenumber);
        if(result) {
            return true;
        }else return false;
    }

    @Override
    public String getEmailByUID(String uid) throws FirebaseAuthException {
        return accountDao.getEmailByUID(uid);
    }

    @Override
    public String getPhonenumberByUID(String uid) throws FirebaseAuthException {
        return accountDao.getPhonenumberByUID(uid);
    }

    @Override
    public List<Account> getAccountByStatus(String status) throws ExecutionException, InterruptedException {
        return accountDao.getAccountByStatus(status);
    }

    @Override
    public Boolean updateStatus(Account account) throws ExecutionException, InterruptedException, FirebaseAuthException {
        return accountDao.changeAccountStatus(account);
        //return accountDao.updateStatus(account, status);
    }

    @Override
    public Account getAccountByUID(String uid) throws ExecutionException, InterruptedException {
        return accountDao.getAccountByUID(uid);
    }

    @Override
    public Boolean saveReasonByUID(String reason, String uid) throws ExecutionException, InterruptedException {
        return accountDao.saveReasonByUID(reason, uid);
    }

    @Override
    public String getReasonByUID(String uid) throws ExecutionException, InterruptedException {
        return accountDao.getReasonByUID(uid);
    }

    @Override
    public Boolean uploadImage(Account account) throws ExecutionException, InterruptedException {
        return accountDao.saveImage(account);
    }
}
