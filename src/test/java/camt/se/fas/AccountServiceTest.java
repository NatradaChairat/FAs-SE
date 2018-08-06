package camt.se.fas;

import camt.se.fas.config.FirebaseConfig;
import camt.se.fas.dao.AccountDao;
import camt.se.fas.entity.Account;
import camt.se.fas.service.AccountService;
import camt.se.fas.service.AccountServiceImpl;
import com.google.firebase.database.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;



@RunWith(SpringRunner.class)
@SpringBootTest
@PrepareForTest({AccountService.class})
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
        System.out.println("Show DR: "+this.databaseReference.getDatabase());
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        /*databaseReference = Mockito.mock(DatabaseReference.class);
        FirebaseDatabase mockFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        when(mockFirebaseDatabase.getReference()).thenReturn(databaseReference);*/

    }

    @Test
    public void testValueSetup(){
        Assert.assertEquals("fas-key-service.json",firebaseConfigPath);
    }

    /*@Test
    public void testaddAccountOfRegistrationStep1(){
        when(accountDaoMock.findLastAccountId()).thenReturn((new Account("FA00001",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null)));
        when(accountDaoMock.addUsernamePasswordStudentId(new Account("FA00001",
                "Marisa1512",
                "Xscd1235",
                "cymerr.cymerr@gmail.com",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null)))
                .thenReturn(new Account("FA00001",
                "Marisa1512",
                "Xscd1235",
                "cymerr.cymerr@gmail.com",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null));

        when(accountDaoMock.addStatus(new Account("FA00001",
                "Marisa1512",
                "Xscd1235",
                "cymerr.cymerr@gmail.com",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null), "registered"))
                .thenReturn(new Account("FA00001",
                        "Marisa1512",
                        "Xscd1235",
                        "cymerr.cymerr@gmail.com",
                        null,
                        null,
                        null,
                        null,
                        null,
                        "registered",
                        null,
                        null));

        when(accountDaoMock.addEmailPhonenumber(new Account("FA00001",
                "Marisa1512",
                "Xscd1235",
                "cymerr.cymerr@gmail.com",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null)))
                .thenReturn(new Account("FA00001",
                        "Marisa1512",
                        "Xscd1235",
                        "cymerr.cymerr@gmail.com",
                        null,
                        null,
                        null,
                        null,
                        null,
                        "registered",
                        null,
                        null));


        Assert.assertEquals(accountService.addAccountOfRegistrationStep1(new Account("FA00001",
                "Marisa1512",
                "Xscd1235",
                "cymerr.cymerr@gmail.com",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null)),new Account("FA00001",
                "Marisa1512",
                "Xscd1235",
                "cymerr.cymerr@gmail.com",
                null,
                null,
                null,
                null,
                null,
                "registered",
                null,
                null));

        verify(accountDaoMock.findLastAccountId());
        verify(accountDaoMock.addUsernamePasswordStudentId(new Account("FA00001",
                "Marisa1512",
                "Xscd1235",
                "cymerr.cymerr@gmail.com",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null)));
        verify(accountDaoMock.addStatus(new Account("FA00001",
                "Marisa1512",
                "Xscd1235",
                "cymerr.cymerr@gmail.com",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null),"registered"));
        verify(accountDaoMock.addEmailPhonenumber(new Account("FA00001",
                "Marisa1512",
                "Xscd1235",
                "cymerr.cymerr@gmail.com",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null)));
    }*/

    /*@Test
    public void findAccountByEmailTest() {
        when(accountDaoMock.findAccountByEmail("songsangmiffy@gmail.com")).thenReturn(new Account("FA00000",
                "Natrada",
                "Zaxs1234",
                "songsangmiffy@gmail.com",
                "Natrada",
                "Chairat",
                "582115019",
                "1997-05-11",
                "0929639169",
                "activated",
                Stream.of("video_2018-10-15 : path/video.mp4",
                        "video_2018-12-1 : path/video.mp4")
                        .collect(Collectors.toList()),
                Stream.of("image_2018-10-15 : [image1 : path/image1.png, image2 : path/image2.png]",
                        "image_2018-2-11 : [image1 : path2/image1.png, image2 : path2/image2.png]" )
                        .collect(Collectors.toList())));
        when(accountDaoMock.findAccountByEmail("cymerr.cymerr@gmail.com")).thenReturn(new Account("FA00001",
                "Marisa1512",
                "Xscd1235",
                "cymerr.cymerr@gmail.com",
                null,
                null,
                null,
                null,
                null,
                "activated",
                null,
                null));


        Assert.assertEquals(accountService.findAccountByEmail("songsangmiffy@gmail.com"), new Account("FA00000",
                "Natrada",
                "Zaxs1234",
                "songsangmiffy@gmail.com",
                "Natrada",
                "Chairat",
                "582115019",
                "1997-05-11",
                "0929639169",
                "activated",
                Stream.of("video_2018-10-15 : path/video.mp4",
                        "video_2018-12-1 : path/video.mp4")
                        .collect(Collectors.toList()),
                Stream.of("image_2018-10-15 : [image1 : path/image1.png, image2 : path/image2.png]",
                        "image_2018-2-11 : [image1 : path2/image1.png, image2 : path2/image2.png]" )
                        .collect(Collectors.toList())));
        Assert.assertEquals(accountService.findAccountByEmail("cymerr.cymerr@gmail.com"),new Account("FA00001",
                "Marisa1512",
                "Xscd1235",
                "cymerr.cymerr@gmail.com",
                null,
                null,
                null,
                null,
                null,
                "activated",
                null,
                null));

        Assert.assertEquals(accountService.findAccountByEmail("cymerr@gmail.com"),null);

        verify(accountDaoMock).findAccountByEmail("songsangmiffy@gmail.com");
        verify(accountDaoMock).findAccountByEmail("cymerr.cymerr@gmail.com");
        verify(accountDaoMock).findAccountByEmail("cymerr@gmail.com");
    }


    @Test
    public void findAccountByUsernameTest() {
        when(accountDaoMock.findAccountByUsername("Natrada")).thenReturn(new Account("FA00000",
                "Natrada",
                "Zaxs1234",
                "songsangmiffy@gmail.com",
                "Natrada",
                "Chairat",
                "582115019",
                "1997-05-11",
                "0929639169",
                "activated",
                Stream.of("video_2018-10-15 : path/video.mp4",
                        "video_2018-12-1 : path/video.mp4")
                        .collect(Collectors.toList()),
                Stream.of("image_2018-10-15 : [image1 : path/image1.png, image2 : path/image2.png]",
                        "image_2018-2-11 : [image1 : path2/image1.png, image2 : path2/image2.png]" )
                        .collect(Collectors.toList())));
        when(accountDaoMock.findAccountByUsername("Marisa1512")).thenReturn(new Account("FA00001",
                "Marisa1512",
                "Xscd1235",
                "cymerr.cymerr@gmail.com",
                null,
                null,
                null,
                null,
                null,
                "activated",
                null,
                null));

        Assert.assertEquals(accountService.findAccountByUsername("Marisa1512"), new Account("FA00001",
                "Marisa1512",
                "Xscd1235",
                "cymerr.cymerr@gmail.com",
                null,
                null,
                null,
                null,
                null,
                "activated",
                null,
                null));
        Assert.assertEquals(accountService.findAccountByUsername("Natrada"),new Account("FA00000",
                "Natrada",
                "Zaxs1234",
                "songsangmiffy@gmail.com",
                "Natrada",
                "Chairat",
                "582115019",
                "1997-05-11",
                "0929639169",
                "activated",
                Stream.of("video_2018-10-15 : path/video.mp4",
                        "video_2018-12-1 : path/video.mp4")
                        .collect(Collectors.toList()),
                Stream.of("image_2018-10-15 : [image1 : path/image1.png, image2 : path/image2.png]",
                        "image_2018-2-11 : [image1 : path2/image1.png, image2 : path2/image2.png]" )
                        .collect(Collectors.toList())));

        Assert.assertEquals(accountService.findAccountByUsername("Siriganya"),null);

        verify(accountDaoMock).findAccountByUsername("Marisa1512");
        verify(accountDaoMock).findAccountByUsername("Natrada");
        verify(accountDaoMock).findAccountByUsername("Siriganya");

    }

    @Test
    public void findAccountByStudentIdTest() {
        when(accountDaoMock.findAccountByStudentId("582115019")).thenReturn(new Account("FA00000",
                "Natrada",
                "Zaxs1234",
                "songsangmiffy@gmail.com",
                "Natrada",
                "Chairat",
                "582115019",
                "1997-05-11",
                "0929639169",
                "activated",
                Stream.of("video_2018-10-15 : path/video.mp4",
                        "video_2018-12-1 : path/video.mp4")
                        .collect(Collectors.toList()),
                Stream.of("image_2018-10-15 : [image1 : path/image1.png, image2 : path/image2.png]",
                        "image_2018-2-11 : [image1 : path2/image1.png, image2 : path2/image2.png]" )
                        .collect(Collectors.toList())));

        Assert.assertEquals(accountService.findAccountByStudentId("582115019"),new Account("FA00000",
                "Natrada",
                "Zaxs1234",
                "songsangmiffy@gmail.com",
                "Natrada",
                "Chairat",
                "582115019",
                "1997-05-11",
                "0929639169",
                "activated",
                Stream.of("video_2018-10-15 : path/video.mp4",
                        "video_2018-12-1 : path/video.mp4")
                        .collect(Collectors.toList()),
                Stream.of("image_2018-10-15 : [image1 : path/image1.png, image2 : path/image2.png]",
                        "image_2018-2-11 : [image1 : path2/image1.png, image2 : path2/image2.png]" )
                        .collect(Collectors.toList())));
        Assert.assertEquals(accountService.findAccountByStudentId("582101002"),null);

        verify(accountDaoMock).findAccountByStudentId("582115019");
        verify(accountDaoMock).findAccountByStudentId("582101002");
    }

    @Test
    public void findAccountByPhonenumberTest() {
        when(accountDaoMock.findAccountByPhonenumber("0929639169")).thenReturn(new Account("FA00000",
                "Natrada",
                "Zaxs1234",
                "songsangmiffy@gmail.com",
                "Natrada",
                "Chairat",
                "582115019",
                "1997-05-11",
                "0929639169",
                "activated",
                Stream.of("video_2018-10-15 : path/video.mp4",
                        "video_2018-12-1 : path/video.mp4")
                        .collect(Collectors.toList()),
                Stream.of("image_2018-10-15 : [image1 : path/image1.png, image2 : path/image2.png]",
                        "image_2018-2-11 : [image1 : path2/image1.png, image2 : path2/image2.png]" )
                        .collect(Collectors.toList())));

        Assert.assertEquals(accountService.findAccountByPhonenumber("0929639169"),new Account("FA00000",
                "Natrada",
                "Zaxs1234",
                "songsangmiffy@gmail.com",
                "Natrada",
                "Chairat",
                "582115019",
                "1997-05-11",
                "0929639169",
                "activated",
                Stream.of("video_2018-10-15 : path/video.mp4",
                        "video_2018-12-1 : path/video.mp4")
                        .collect(Collectors.toList()),
                Stream.of("image_2018-10-15 : [image1 : path/image1.png, image2 : path/image2.png]",
                        "image_2018-2-11 : [image1 : path2/image1.png, image2 : path2/image2.png]" )
                        .collect(Collectors.toList())));
        Assert.assertEquals(accountService.findAccountByPhonenumber("053491100"),null);

        verify(accountDaoMock).findAccountByPhonenumber("0929639169");
        verify(accountDaoMock).findAccountByPhonenumber("053491100");

    }
    @Test
    public void getAccountTest() {
        when(accountDaoMock.findAccountByAccountId("FA00000")).thenReturn(new Account("FA00000",
                "Natrada",
                "Zaxs1234",
                "songsangmiffy@gmail.com",
                "Natrada",
                "Chairat",
                "582115019",
                "1997-05-11",
                "0929639169",
                "activated",
                Stream.of("video_2018-10-15 : path/video.mp4",
                        "video_2018-12-1 : path/video.mp4")
                        .collect(Collectors.toList()),
                Stream.of("image_2018-10-15 : [image1 : path/image1.png, image2 : path/image2.png]",
                        "image_2018-2-11 : [image1 : path2/image1.png, image2 : path2/image2.png]" )
                        .collect(Collectors.toList())));
        when(accountDaoMock.findAccountByAccountId("FA00001")).thenReturn(new Account("FA00001",
                "Marisa1512",
                "Xscd1235",
                "cymerr.cymerr@gmail.com",
                null,
                null,
                null,
                null,
                null,
                "activated",
                null,
                null));


        Assert.assertEquals(accountService.getAccount("FA00000"), new Account("FA00000",
                "Natrada",
                "Zaxs1234",
                "songsangmiffy@gmail.com",
                "Natrada",
                "Chairat",
                "582115019",
                "1997-05-11",
                "0929639169",
                "activated",
                Stream.of("video_2018-10-15 : path/video.mp4",
                        "video_2018-12-1 : path/video.mp4")
                        .collect(Collectors.toList()),
                Stream.of("image_2018-10-15 : [image1 : path/image1.png, image2 : path/image2.png]",
                        "image_2018-2-11 : [image1 : path2/image1.png, image2 : path2/image2.png]" )
                        .collect(Collectors.toList())));
        Assert.assertEquals(accountService.getAccount("FA00001"),new Account("FA00001",
                "Marisa1512",
                "Xscd1235",
                "cymerr.cymerr@gmail.com",
                null,
                null,
                null,
                null,
                null,
                "activated",
                null,
                null));

        Assert.assertEquals(accountService.getAccount("FA99999"),null);

        verify(accountDaoMock).findAccountByAccountId("FA00000");
        verify(accountDaoMock).findAccountByAccountId("FA00001");
        verify(accountDaoMock).findAccountByAccountId("FA99999");

    }*/







}
