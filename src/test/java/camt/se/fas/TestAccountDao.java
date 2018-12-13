package camt.se.fas;


import camt.se.fas.config.FirebaseConfig;
import camt.se.fas.dao.AccountDao;
import camt.se.fas.dao.AccountDaoImpl;
import camt.se.fas.entity.Account;
import com.google.firebase.auth.FirebaseAuthException;
import javafx.beans.binding.When;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = {"firebase.config.path=fas-key-service.json","firebase.database-url=https://facialauthentication-camt.firebaseio.com"})
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class TestAccountDao {

    AccountDao accountDao = new AccountDaoImpl();

    @Configuration
    @Import({FirebaseConfig.class})
    static class ContextConfiguration {
    }

    FirebaseConfig firebaseConfig;
    @Autowired
    public void setDatabaseReference (FirebaseConfig firebaseConfig) {
        this.firebaseConfig = firebaseConfig;
    }

    @Test
    public void testCreateAccount(){ //
        try {
            String uid = accountDao.createAccount(new Account(null,
                    "Zaxs1234",
                    "ch.ai@gmail.com",
                    null, null,
                    null, null,
                    null, null,
                    null, null));

            Assert.assertEquals(uid, uid);
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddAccountInfo() { //
        try {
            ArrayList<String> images = new ArrayList<>();
            images.add("https://firebasestorage.googleapis.com/v0/b/facialauthentication-camt.appspot.com/o/faceRegister%2F65864365e598296cef9fe3e1cba0dbfab0820f1253496c6ae7d44dced9142a921112180706?alt=media&token=f501c965-795f-4474-8c60-490fd3c12389");

            boolean result = accountDao.addAccountInfo(new Account("h4x2H18A9UcULfqr6XVswxHs6I63",
                    null,
                    null,
                    "Siriganya", "Sen",
                    "582115044","1996-10-10",
                    "+66929639169",null,
                    "QDHRNM",images));
            Assert.assertEquals(true,result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testChangeAccountStatus(){ //

        try {
            boolean result = accountDao.changeAccountStatus(new Account("h4x2H18A9UcULfqr6XVswxHs6I63",
                    null,
                    null,
                    null, null,
                    null,null,
                    null,"disapproved",
                    null,null));
            Assert.assertEquals(true,result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSaveImage(){ //
        try {

            ArrayList<String> images = new ArrayList<>();
            images.add("https://firebasestorage.googleapis.com/v0/b/facialauthentication-camt.appspot.com/o/faceTrain%2FC7B5C7D737297ED72826F2AAF1463D057E147C621012180141?alt=media&token=d71a428d-15c5-4f4b-b2ce-ff3b38976f46");

            boolean result = accountDao.changeAccountStatus(new Account("h4x2H18A9UcULfqr6XVswxHs6I63",
                    null,
                    null,
                    null, null,
                    null,null,
                    null,null,
                    null,images));
            Assert.assertEquals(true,result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSaveReasonByUID(){ //
        try {
            boolean result = accountDao.saveReasonByUID("2", "h4x2H18A9UcULfqr6XVswxHs6I63");

            Assert.assertEquals(true,result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetAccountByUID(){ //
        try {
            ArrayList<String> images = new ArrayList<>();
            images.add("https://firebasestorage.googleapis.com/v0/b/facialauthentication-camt.appspot.com/o/faceRegister%2F65864365e598296cef9fe3e1cba0dbfab0820f1253496c6ae7d44dced9142a921112180706?alt=media&token=f501c965-795f-4474-8c60-490fd3c12389");

            Account result = accountDao.getAccountByUID("h4x2H18A9UcULfqr6XVswxHs6I63");
            Assert.assertEquals(new Account("h4x2H18A9UcULfqr6XVswxHs6I63",
                    null,
                    null,
                    "Siriganya","Sen",
                    "582115044","1996-10-10",
                    "+66929639169","registered",
                    "QDHRNM",images),result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetEmailByUID(){ //
        try {
            String result = accountDao.getEmailByUID("h4x2H18A9UcULfqr6XVswxHs6I63");

            Assert.assertEquals("siriganya_sen@outlook.com", result);
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetPhonenumberByUID(){ //
        try {
            String result = accountDao.getPhonenumberByUID("h4x2H18A9UcULfqr6XVswxHs6I63");

            Assert.assertEquals("+66929639169", result);
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testFindAccountByStudentID(){ //
        try {
            Boolean result = accountDao.findAccountByStudentId("582115044");
            Assert.assertEquals(true, result);

            Boolean result2 = accountDao.findAccountByStudentId("582115000");
            Assert.assertEquals(false, result2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindAccountByPhonenumber(){ //
        try {
            Boolean result = accountDao.findAccountByphonenumber("+66929639169");
            Assert.assertEquals(true, result);

            Boolean result2 = accountDao.findAccountByphonenumber("+66959919955");
            Assert.assertEquals(false, result2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetAccountByType(){ //
        try {
            ArrayList<Account> accounts = new ArrayList<>();
            accounts.add(new Account("GF7ew8XShNeU8jvovRyI5vhJXwi1",
                    null,
                    null,
                    "Natrada", "Chairat",
                    null, null,
                    null, null,
                    null,null));
            List<Account> result = accountDao.getAccountByType("staff");

            Assert.assertEquals(accounts, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetAccountByStatus(){//
        try {
            ArrayList<String> images = new ArrayList<>();
            images.add("https://firebasestorage.googleapis.com/v0/b/facialauthentication-camt.appspot.com/o/faceRegister%2F65864365e598296cef9fe3e1cba0dbfab0820f1253496c6ae7d44dced9142a921112180706?alt=media&token=f501c965-795f-4474-8c60-490fd3c12389");
            List<Account> result = accountDao.getAccountByStatus("registered");

            ArrayList<Account> accounts = new ArrayList<>();
            accounts.add(new Account("h4x2H18A9UcULfqr6XVswxHs6I63",
                    null,
                    null,
                    "Siriganya","Sen",
                    "582115044","1996-10-10",
                    "+66929639169","registered",
                    "QDHRNM",images));
            Assert.assertEquals(accounts, result);

            List<Account> result2 = accountDao.getAccountByStatus("approved");
            Assert.assertEquals(null, result2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetReasonByUID(){ //
        try {
            String result = accountDao.getReasonByUID("h4x2H18A9UcULfqr6XVswxHs6I63");
            Assert.assertEquals("2", result);

            String result2 = accountDao.getReasonByUID("GF7ew8XShNeU8jvovRyI5vhJXwi1");
            Assert.assertEquals(null, result2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
