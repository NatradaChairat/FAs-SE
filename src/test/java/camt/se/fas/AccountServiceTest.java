package camt.se.fas;

import camt.se.fas.service.AccountService;
import camt.se.fas.service.AccountServiceImpl;
import com.google.api.client.googleapis.testing.TestUtils;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
public class AccountServiceTest {

    @Autowired
    private AccountService accountService = new AccountServiceImpl();

    @Value("${firebase.database-url}")
    String firebaseUrl;

    @Value("${firebase.config.path}")
    String firebaseConfigPath;

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
        String result = accountService.findAccountByEmail("natrada@cmu.ac.th");

        System.out.println("Test: "+result);
        Assert.assertEquals("",result);
    }




}
