package camt.se.fas;

import camt.se.fas.service.AES;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AESServiceTest {
    AES aes = new AES();
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
