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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@PrepareForTest({ FirebaseDatabase.class})
@TestPropertySource(properties = {"firebase.config.path=fas-key-service.json","firebase.database-url=https://facialauthentication-camt.firebaseio.com"})
public class AccountServiceTest {

    @Mock AccountDao accountDaoMock /*= mock(AccountDao.class)*/;
    @InjectMocks AccountService accountService = new AccountServiceImpl();

    /*@Autowired
    private AccountService accountService = new AccountServiceImpl();*/

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

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testValueSetup(){
        Assert.assertEquals("fas-key-service.json",firebaseConfigPath);
    }

    @Test
    public void testFindAccountByEmail(){
        accountDaoMock.findAccountByEmail("natrada_chairat@cmu.ac.th");
    }

    @Test
    public void findAccountByEmailTest() {
        when(accountDaoMock.findAccountByEmail("natrada_chairat@cmu.ac.th")).thenReturn(new Account("FA00000",
                null,
                null,
                "natrada_chairat@cmu.ac.th",
                null,
                null,
                null,
                null,
                "0929639169",
                null,
                null,
                null));
        when(accountDaoMock.findAccountByEmail("songsangmiffy@gmail.com")).thenReturn(new Account("FA00001",
                null,
                null,
                "songsangmiffy@gmail.com",
                null,
                null,
                null,
                null,
                "0929639997",
                null,
                null,
                null));
        when(accountDaoMock.findAccountByEmail("cymerr.cymerr@gmail.com")).thenReturn(new Account("FA00002",
                null,
                null,
                "cymerr.cymerr@gmail.com",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null));


        Assert.assertEquals(accountService.findAccountByEmail("natrada_chairat@cmu.ac.th"), new Account("FA00000",
                null,
                null,
                "natrada_chairat@cmu.ac.th",
                null,
                null,
                null,
                null,
                "0929639169",
                null,
                null,
                null));
        Assert.assertEquals(accountService.findAccountByEmail("cymerr.cymerr@gmail.com"),new Account("FA00002",
                null,
                null,
                "cymerr.cymerr@gmail.com",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null));

        Assert.assertEquals(accountService.findAccountByEmail("cymerr@gmail.com"),null);

    }


    @Test
    public void findAccountByUsernameTest() {
        when(accountDaoMock.findAccountByUsername("Natrada")).thenReturn(new Account("FA00000",
                "Natrada",
                "Gy1515po",
                null,
                null,
                null,
                "582115019",
                null,
                null,
                null,
                null,
                null));
        when(accountDaoMock.findAccountByUsername("Natrada112")).thenReturn(new Account("FA00001",
                "Natrada112",
                "Zaxs1234",
                null,
                null,
                null,
                "582113337",
                null,
                null,
                null,
                null,
                null));
        when(accountDaoMock.findAccountByUsername("Marisa1512")).thenReturn(new Account("FA00002",
                "Marisa1512",
                "Xscd1235",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null));


        Assert.assertEquals(accountService.findAccountByUsername("Marisa1512"), new Account("FA00002",
                "Marisa1512",
                "Xscd1235",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null));
        Assert.assertEquals(accountService.findAccountByUsername("Natrada112"),new Account("FA00001",
                "Natrada112",
                "Zaxs1234",
                null,
                null,
                null,
                "582113337",
                null,
                null,
                null,
                null,
                null));

        Assert.assertEquals(accountService.findAccountByUsername("cymerr"),null);

    }

    @Test
    public void findAccountByStudentIdTest() {
        when(accountDaoMock.findAccountByStudentId("582115019")).thenReturn(new Account("FA00000",
                "Natrada",
                "Gy1515po",
                null,
                null,
                null,
                "582115019",
                null,
                null,
                null,
                null,
                null));
        when(accountDaoMock.findAccountByStudentId("582113337")).thenReturn(new Account("FA00001",
                "Natrada112",
                "Zaxs1234",
                null,
                null,
                null,
                "582113337",
                null,
                null,
                null,
                null,
                null));


        Assert.assertEquals(accountService.findAccountByStudentId("582113337"), new Account("FA00001",
                "Natrada112",
                "Zaxs1234",
                null,
                null,
                null,
                "582113337",
                null,
                null,
                null,
                null,
                null));
        Assert.assertEquals(accountService.findAccountByStudentId("582115019"),new Account("FA00000",
                "Natrada",
                "Gy1515po",
                null,
                null,
                null,
                "582115019",
                null,
                null,
                null,
                null,
                null));

        Assert.assertEquals(accountService.findAccountByUsername("582101002"),null);

    }

    @Test
    public void findAccountByPhonenumberTest() {
        when(accountDaoMock.findAccountByPhonenumber("0929639169")).thenReturn(new Account("FA00000",
                null,
                null,
                "natrada_chairat@cmu.ac.th",
                null,
                null,
                null,
                null,
                "092639169",
                null,
                null,
                null));
        when(accountDaoMock.findAccountByPhonenumber("0929639997")).thenReturn(new Account("FA00001",
                null,
                null,
                "songsangmiffy@gmail.com",
                null,
                null,
                null,
                null,
                "0929639997",
                null,
                null,
                null));



        Assert.assertEquals(accountService.findAccountByPhonenumber("0929639997"), new Account("FA00001",
                null,
                null,
                "songsangmiffy@gmail.com",
                null,
                null,
                null,
                null,
                "0929639997",
                null,
                null,
                null));
        Assert.assertEquals(accountService.findAccountByPhonenumber("0929639169"),new Account("FA00000",
                null,
                null,
                "natrada_chairat@cmu.ac.th",
                null,
                null,
                null,
                null,
                "092639169",
                null,
                null,
                null));
        Assert.assertEquals(accountService.findAccountByPhonenumber("053491100"),null);

    }
    @Test
    public void getAccountTest() {
        when(accountDaoMock.findAccountByAccountId("FA00000")).thenReturn(new Account("FA00000",
                "Natrada",
                null,
                "natrada_chairat@cmu.ac.th",
                null,
                null,
                null,
                null,
                null,
                "activated",
                null,
                null));
        when(accountDaoMock.findAccountByAccountId("FA00001")).thenReturn(new Account("FA00001",
                "Natrada112",
                null,
                "songsangmiffy@gmail.com",
                null,
                null,
                null,
                null,
                null,
                "activated",
                null,
                null));

        when(accountDaoMock.findAccountByAccountId("FA00002")).thenReturn(new Account("FA00002",
                "Marisa1512",
                null,
                "cymerr.cymerr@gmail.com",
                null,
                null,
                null,
                null,
                null,
                "registered",
                null,
                null));

        Assert.assertEquals(accountService.getAccount("FA00000"), new Account("FA00000",
                "Natrada",
                null,
                "natrada_chairat@cmu.ac.th",
                null,
                null,
                null,
                null,
                null,
                "activated",
                null,
                null));
        Assert.assertEquals(accountService.getAccount("FA00001"),new Account("FA00001",
                "Natrada112",
                null,
                "songsangmiffy@gmail.com",
                null,
                null,
                null,
                null,
                null,
                "activated",
                null,
                null));

        Assert.assertEquals(accountService.findAccountByPhonenumber("FA99999"),null);

    }







}
