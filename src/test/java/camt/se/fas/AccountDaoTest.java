package camt.se.fas;

import camt.se.fas.config.FirebaseConfig;
import camt.se.fas.dao.old.AccountDaoImpl;
import camt.se.fas.entity.Account;
import com.google.firebase.database.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


//@RunWith(PowerMockRunner.class)
//@PowerMockRunnerDelegate(JUnit4.class)
//@PowerMockIgnore("javax.management.*")
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {"firebase.config.path=fas-key-service.json","firebase.database-url=https://facialauthentication-camt.firebaseio.com"})
public class AccountDaoTest {
    @Value("${firebase.database-url}")
    String firebaseUrl;

    @Value("${firebase.config.path}")
    String firebaseConfigPath;


    private DatabaseReference databaseReference;

    @Mock
    private DatabaseReference mockDatabaseRef;

    FirebaseConfig firebaseConfig;


    AccountDaoImpl accountDao = new AccountDaoImpl();

    @Autowired
    public void setDatabaseReference (FirebaseConfig firebaseConfig) {
        System.out.println("Setup");
        this.databaseReference = firebaseConfig.firebaseDatabase();
        /*InputStream serviceAccount = FirebaseConfig.class.getClassLoader().getResourceAsStream(firebaseConfigPath);
        testApp = FirebaseApp.initializeApp(
                new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl(firebaseUrl)
                        .build()
        );*/
    }

   @Test
   public void testValueSetup(){
       Assert.assertEquals("fas-key-service.json",firebaseConfigPath);
   }

    @Before
    public void setUp() {

        /*mockDatabaseRef = Mockito.mock(DatabaseReference.class);

        FirebaseDatabase mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        when(mockedFirebaseDatabase.getReference()).thenReturn(mockDatabaseRef);

        PowerMockito.mockStatic(FirebaseDatabase.class);
        when(FirebaseDatabase.getInstance()).thenReturn(mockedFirebaseDatabase);*/

    }

    @Test
    public void testFindAccountByEmail(){
       /* when(mockDatabaseRef.child(anyString())).thenReturn(mockDatabaseRef);
        doAnswer(new Answer<Account>() {
            @Override
            public Account answer(InvocationOnMock invocation) throws Throwable {
                ValueEventListener valueEventListener  = (ValueEventListener) invocation.getArguments()[0];
                DataSnapshot mockDataSnapshot = Mockito.mock(DataSnapshot.class);
                valueEventListener.onDataChange(mockDataSnapshot);
                return null;
            }
        }).when(mockDatabaseRef).addListenerForSingleValueEvent(any(ValueEventListener.class));
        Assert.assertEquals(null, accountDao.findAccountByEmail("Cyymerr"));*/
    }

    @Test
    public void testFindAccountByEmail2() {
        //accountDao.setDatabaseReference(firebaseConfig);
        when(mockDatabaseRef.child(anyString())).thenReturn(databaseReference.child("account"));
        System.out.println(mockDatabaseRef.child(""));
        Account account = accountDao.addUsernamePasswordStudentId(new Account("FA00002",
                "Marisa",
                "Xscd1235",
                "cymerr@gmail.com",
                null,
                null,
                null,
                null,
                null,
                "activated",
                null,
                null));

        System.out.println(account);


    }
}
