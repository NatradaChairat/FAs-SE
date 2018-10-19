package camt.se.fas;


import camt.se.fas.config.FirebaseConfig;
import camt.se.fas.dao.AccountDao;
import camt.se.fas.dao.AccountDaoImpl;
import camt.se.fas.entity.Account;
import com.google.firebase.auth.FirebaseAuthException;
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

import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
//@SpringBootTest
@TestPropertySource(properties = {"firebase.config.path=fas-key-service.json","firebase.database-url=https://facialauthentication-camt.firebaseio.com"})
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class AccountDaoTest {
    AccountDao accountDao = new AccountDaoImpl();

    /*@Value("${firebase.database-url}")
    String firebaseUrl;

    @Value("${firebase.config.path}")
    String firebaseConfigPath;*/
    @Configuration
    @Import({FirebaseConfig.class})
    static class ContextConfiguration {
    }

    FirebaseConfig firebaseConfig;
    @Autowired
    public void setDatabaseReference (FirebaseConfig firebaseConfig) {
        this.firebaseConfig = firebaseConfig;
    }


    /*@Test
    public void testCreateAccount(){
        try {
            String result = accountDao.createAccount(new Account(null,
                    "Zaxs1234",
                    "Songsangmiffy@gmail.com",
                    null, null,
                    null,null,
                    null,null,
                    null,null));
            Assert.assertEquals("",result);
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
    }*/

    @Test
    public void testAddStatus() {
        try {
            boolean result = accountDao.addStatus(new Account("ZjGtiZndKySFhgkesF8R7WIO8pp1",
                    "Zaxs1234",
                    "Songsangmiffy@gmail.com",
                    null, null,
                    null,null,
                    null,null,
                    null,null));
            Assert.assertEquals(true,result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindAocIdByStudentId() {
        try {
            String result = accountDao.findAccountByStudentId("582115019");
            Assert.assertEquals("05nWOPMFAEIlzvb2fg38",result);
            String result2 = accountDao.findAccountByStudentId("582115000");
            Assert.assertEquals(null,result2);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindAocIdByPhonenumber() {
        try {
            String result = accountDao.findAccountByphonenumber("+66929639169");
            Assert.assertEquals("05nWOPMFAEIlzvb2fg38",result);
            String result2 = accountDao.findAccountByphonenumber("+66959919955");
            Assert.assertEquals(null,result2);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindEmailByUID() {
        try {
            String result = accountDao.findEmailByUID("cpyEQtswYHXCiN4omI9nBrUXZpz1");
            Assert.assertEquals("natrada_chairat@cmu.ac.th",result);

        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testFindPhonenumberByUID() {
        try {
            String result = accountDao.getPhonenumberByUID("cpyEQtswYHXCiN4omI9nBrUXZpz1");
            Assert.assertEquals("+66929639169",result);

        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateStatus() {
        try {
            boolean result = accountDao.updateStatus(new Account("ZjGtiZndKySFhgkesF8R7WIO8pp1",
                    null,
                    null,
                    null, null,
                    null,null,
                    null,null,
                    null,null),"activated");
            Assert.assertEquals(true,result);
            boolean result2 = accountDao.updateStatus(new Account("ZjGtiZndKySFhgkesF8R7WIO8pp1_",
                    null,
                    null,
                    null, null,
                    null,null,
                    null,null,
                    null,null),"activated");
            Assert.assertEquals(false,result2);

        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddAccountInfo() {
        try {
            boolean result = accountDao.addAccountInfo(new Account("ZjGtiZndKySFhgkesF8R7WIO8pp1",
                    null,
                    null,
                    "Siriganya2", "Sensupa",
                    "582115044","1997-11-30",
                    "+66635168449",null,
                    null,null));
            Assert.assertEquals(true,result);
            boolean result2 = accountDao.addAccountInfo(new Account("ZjGtiZndKySFhgkesF8R7WIO8pp1_",
                    null,
                    null,
                    "Siriganya", "Sensupa",
                    "582115044","1997-11-30",
                    "+66635168449",null,
                    null,null));
            Assert.assertEquals(false,result2);

        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
