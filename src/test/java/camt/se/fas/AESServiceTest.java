package camt.se.fas;

import camt.se.fas.config.FirebaseConfig;


import camt.se.fas.service.AESService;
import camt.se.fas.service.AESServiceImpl;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class AESServiceTest {
    @Configuration
    //@Import({FirebaseConfig.class})
    static class ContextConfiguration {
    }
    AESService aes = new AESServiceImpl();
    /*@Rule
    public final ExpectedException exception = ExpectedException.none();
    */
    @Test
    public void test_encrypt_WhenCanReturnValue(){
        Assert.assertEquals("3Ax22q%2BUJ4XA8XZykUwFTbKfVWKw0T6upp3QjCgJBpk%3D",aes.encrypt("ZHK6T6FfVTUnf8vJTcYDDzvIoLu1"));
        Assert.assertEquals("HWmKYsiYSnApQmubivl0ZQ%3D%3D",aes.encrypt("FA00002"));

    }
    @Test
    public void test_encrypt_WhenCatchException(){

    }

    @Test
    public void test_decrypt_WhenCannotReturnValue(){
        Assert.assertEquals("FA00000",aes.decrypt("PYw%2FvdxDKF%2FeKMM8nS%2BVtw%3D%3D"));
        Assert.assertEquals("FA00002",aes.decrypt("HWmKYsiYSnApQmubivl0ZQ%3D%3D"));

    }
    @Test/*(expected = Exception.class)*/
    public void test_decrypt_WhenCatchException(){

       /* try {
            aes.decrypt("HWmKYsiYSnApQmubivl0ZQ%3D%3D");
            Assert.fail();
        }catch (Exception e){

        }*/

    }



}
