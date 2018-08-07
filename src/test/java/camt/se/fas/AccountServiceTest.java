package camt.se.fas;

import camt.se.fas.config.FirebaseConfig;
import camt.se.fas.dao.AccountDao;
import camt.se.fas.dao.AccountDaoImpl;
import camt.se.fas.entity.Account;
import camt.se.fas.service.AccountService;
import camt.se.fas.service.AccountServiceImpl;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;



@RunWith(SpringRunner.class)
@TestPropertySource(properties = {"firebase.config.path=fas-key-service.json","firebase.database-url=https://facialauthentication-camt.firebaseio.com"})
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class AccountServiceTest {
    AccountService accountService = new AccountServiceImpl();
    /*@Mock
    AccountDao accountDao;*/
    @Configuration
    @Import({FirebaseConfig.class,AccountDaoImpl.class})
    static class ContextConfiguration {
    }

    FirebaseConfig firebaseConfig;
    @Autowired
    public void setDatabaseReference (FirebaseConfig firebaseConfig) {
        this.firebaseConfig = firebaseConfig;
    }
    @Test
    public void testRegisterAccount(){
        AccountDao accountDao = new AccountDaoImpl();
        try {
            //Test pass
            when(accountDao.createAccount(new Account(null,
                    "Zaxs1234",
                    "Songsangmiffy@gmail.com",
                    null, null,
                    null,null,
                    null,null,
                    null,null))).thenReturn("ZjGtiZndKySFhgkesF8R7WIO8pp1");
            when(accountDao.addStatus(new Account("ZjGtiZndKySFhgkesF8R7WIO8pp1",
                    "Zaxs1234",
                    "Songsangmiffy@gmail.com",
                    null, null,
                    null,null,
                    null,null,
                    null,null))).thenReturn(true);
           /* verify(accountDao.addStatus(new Account(null,
                    "Zaxs1234",
                    "Songsangmiffy@gmail.com",
                    null, null,
                    null,null,
                    null,null,
                    null,null)));*/
            Assert.assertEquals("ZjGtiZndKySFhgkesF8R7WIO8pp1",accountService.registerAccount(new Account(null,
                    "Zaxs1234",
                    "Songsangmiffy@gmail.com",
                    null, null,
                    null,null,
                    null,null,
                    null,null)));

            //Test cannot add status
            when(accountDao.createAccount(new Account(null,
                    "Zaxs1234",
                    "Natrada@gmail.com",
                    null, null,
                    null,null,
                    null,null,
                    null,null))).thenReturn("something");
            when(accountDao.addStatus(new Account("something",
                    "Zaxs1234",
                    "Natrada@gmail.com",
                    null, null,
                    null,null,
                    null,null,
                    null,null))).thenReturn(false);
            accountService.registerAccount(new Account(null,
                    "Zaxs1234",
                    "Natrada@gmail.com",
                    null, null,
                    null,null,
                    null,null,
                    null,null));
            /*verify(accountDao.addStatus(new Account(null,
                    "Zaxs1234",
                    "Natrada@gmail.com",
                    null, null,
                    null,null,
                    null,null,
                    null,null)));*/
            Assert.assertEquals(null, accountService.registerAccount(new Account(null,
                    "Zaxs1234",
                    "Natrada@gmail.com",
                    null, null,
                    null,null,
                    null,null,
                    null,null)));
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRegisterAccountInfo(){
        AccountDao accountDao = new AccountDaoImpl();
        try {
            when(accountDao.addAccountInfo(any())).thenReturn(true);
            Assert.assertEquals(true, accountService.registerAccountInfo(any()));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testChckDuplicatedStudentId(){}
    @Test
    public void testChckDuplicatedPhonenumber(){}

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
