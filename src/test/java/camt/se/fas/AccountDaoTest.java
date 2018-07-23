package camt.se.fas;

import camt.se.fas.config.FirebaseConfig;
import camt.se.fas.dao.AccountDao;
import camt.se.fas.dao.AccountDaoImpl;
import camt.se.fas.entity.Account;
import camt.se.fas.service.AccountService;
import camt.se.fas.service.AccountServiceImpl;
import com.google.api.client.googleapis.testing.TestUtils;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@PrepareForTest({ FirebaseDatabase.class})
@TestPropertySource(properties = {"firebase.config.path=fas-key-service.json","firebase.database-url=https://facialauthentication-camt.firebaseio.com"})
public class AccountDaoTest {

    @Autowired
    private AccountDao accountDao = new AccountDaoImpl();

    @Value("${firebase.database-url}")
    String firebaseUrl;

    @Value("${firebase.config.path}")
    String firebaseConfigPath;

    DatabaseReference databaseReference;

    @Autowired
    public void setDatabaseReference (FirebaseConfig firebaseConfig){
        System.out.println("Setup");
        this.databaseReference = firebaseConfig.firebaseDatabase();
    }

/*
 public static final FirebaseOptions options =
         InputStream serviceAccount = AccountServiceImpl.class.getClassLoader().getResourceAsStream(firebaseConfigPath);
    GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
    FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(credentials)
            //.setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl(firebaseUrl)
            .build();
*/


    @Test
    public void testValueSetup(){
        Assert.assertEquals("fas-key-service.json",firebaseConfigPath);
    }

    @Test
    public void test_findAccountEmail_emailIsNotExited(){
        //Now, return null | addListenerForSingleValueEvent not working on Test Class
        Account result = accountDao.findAccountByEmail("songsangmiffy@gmail.com");
        //Account result = accountService.findAccountByEmail("songsangmiffy@gmail.com");

        System.out.println("Test: "+result);
        Assert.assertEquals("",result);
    }




}
