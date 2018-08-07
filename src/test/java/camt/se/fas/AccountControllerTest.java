package camt.se.fas;

import camt.se.fas.config.FirebaseConfig;
import camt.se.fas.controller.AccountController;
import camt.se.fas.entity.Account;
import camt.se.fas.service.AESService;
import camt.se.fas.service.AccountService;
import camt.se.fas.service.EmailService;
import camt.se.fas.service.SMSService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.json.Json;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuthException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static jdk.nashorn.internal.objects.NativeJSON.stringify;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = {"firebase.config.path=fas-key-service.json","firebase.database-url=https://facialauthentication-camt.firebaseio.com"})
//@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    /*@Configuration
    @Import({FirebaseConfig.class})
    static class ContextConfiguration {
        FasApplication
    }*/

    FirebaseConfig firebaseConfig;

    @Autowired
    public void setDatabaseReference(FirebaseConfig firebaseConfig) {
        this.firebaseConfig = firebaseConfig;
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;
    @MockBean
    private AESService aesService;
    @MockBean
    private EmailService emailService;
    @MockBean
    private SMSService smsService;

    @Test
    public void testUploadAccount_canStoreData() throws Exception {
        Account account = new Account(null,
                "Zaxs1234",
                "Songsangmiffy@gmail.com",
                null, null,
                null, null,
                null, null,
                null, null);
        ObjectMapper mapper = new ObjectMapper();

        BDDMockito.given(accountService.registerAccount(account)).willReturn("ZjGtiZndKySFhgkesF8R7WIO8pp1");
        BDDMockito.given(aesService.encrypt("ZjGtiZndKySFhgkesF8R7WIO8pp1")).willReturn("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D");
        mockMvc.perform(MockMvcRequestBuilders.post("/account/create")
                .content(mapper.writeValueAsString(account))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D"));
        // .andExpect((ResultMatcher) jsonPath("$[0].name", is("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")));
    }
    @Test
    public void testUploadAccount_cannotStoreData() throws Exception {
        Account account = new Account(null,
                "Zaxs1234",
                "Songsangmiffy@gmail.com",
                null, null,
                null, null,
                null, null,
                null, null);
        ObjectMapper mapper = new ObjectMapper();

        BDDMockito.given(accountService.registerAccount(account)).willThrow(new InterruptedException());
        BDDMockito.given(aesService.encrypt("ZjGtiZndKySFhgkesF8R7WIO8pp1")).willReturn("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D");
        mockMvc.perform(MockMvcRequestBuilders.post("/account/create")
                .content(mapper.writeValueAsString(account))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
                //.andExpect(content().string("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D"));
        // .andExpect((ResultMatcher) jsonPath("$[0].name", is("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")));

    }

    @Test
    public void testUpdateAccount_canStoreData() throws Exception {
        Account account = new Account(null,
                null,
                null,
                "Siriganya", "Sensupa",
                "582115044", "1997-11-30",
                "+66635168449", null,
                null, null);
        ObjectMapper mapper = new ObjectMapper();

        BDDMockito.given(accountService.registerAccountInfo(account)).willReturn(true);
        BDDMockito.given(aesService.encrypt("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")).willReturn("ZjGtiZndKySFhgkesF8R7WIO8pp1");
        mockMvc.perform(MockMvcRequestBuilders.post("/account/update/{param}","0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")
                .content(mapper.writeValueAsString(account))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(true)));
                //.andExpect(content().string("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D"));
        // .andExpect((ResultMatcher) jsonPath("$[0].name", is("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")));
    }
    @Test
    public void testUpdateAccount_cannotStoreData() throws Exception {
        Account account = new Account(null,
                null,
                null,
                "Siriganya", "Sensupa",
                "582115044", "1997-11-30",
                "+66635168449", null,
                null, null);
        ObjectMapper mapper = new ObjectMapper();

        BDDMockito.given(accountService.registerAccountInfo(account)).willReturn(false);
        BDDMockito.given(aesService.encrypt("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")).willReturn("ZjGtiZndKySFhgkesF8R7WIO8pp1");
        mockMvc.perform(MockMvcRequestBuilders.post("/account/update/{param}","0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")
                .content(mapper.writeValueAsString(account))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(false)));
        //.andExpect(content().string("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D"));
        // .andExpect((ResultMatcher) jsonPath("$[0].name", is("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")));
    }
    @Test
    public void testUpdateAccount_Exception() throws Exception{
            Account account = new Account(null,
                null,
                null,
                "Siriganya", "Sensupa",
                "582115044", "1997-11-30",
                "+66635168449", null,
                null, null);
        ObjectMapper mapper = new ObjectMapper();

        BDDMockito.given(accountService.registerAccountInfo(account)).willThrow(new InterruptedException());

        BDDMockito.given(aesService.encrypt("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")).willReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/account/update/{param}","0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")
                .content(mapper.writeValueAsString(account))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

   /* @Test
    public void testSendPhonumber() throws Exception{
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("otp",String.valueOf(445533));
        responseHeaders.set("refCode",String.valueOf("CdVFcd"));
        responseHeaders.set("phonenumber",String.valueOf("+66929639169"));
        BDDMockito.given(aesService.decrypt("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")).willReturn("ZjGtiZndKySFhgkesF8R7WIO8pp1");
        BDDMockito.given(accountService.getPhonenumberByUID("ZjGtiZndKySFhgkesF8R7WIO8pp1")).willReturn("+66929639169");
        BDDMockito.given(smsService.sendSMS("+66929639169","CdVFcd",445533)).willReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.get("/account/send/phonenumber/{param}","0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")
                //.content(mapper.writeValueAsString(account))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(responseHeaders)));
    }*/



    @Test
    public void testSendEmail_canSendData() throws Exception {
        BDDMockito.given(aesService.decrypt("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")).willReturn("ZjGtiZndKySFhgkesF8R7WIO8pp1");
        BDDMockito.given(accountService.getEmailByUID("ZjGtiZndKySFhgkesF8R7WIO8pp1")).willReturn("songsangmiffy@gmail.com");
        BDDMockito.given(emailService.sendEmail("songsangmiffy@gmail.com","ZjGtiZndKySFhgkesF8R7WIO8pp1")).willReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.get("/account/send/email/{param}","0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")
                //.content(mapper.writeValueAsString(account))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(true)));
        //.andExpect(content().string("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D"));
        // .andExpect((ResultMatcher) jsonPath("$[0].name", is("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")));
    }

    /*@Test
    public void testSendEmail_Exception() throws Exception {
        BDDMockito.given(aesService.decrypt("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")).willReturn("ZjGtiZndKySFhgkesF8R7WIO8pp1");
        BDDMockito.given(accountService.getEmailByUID("ZjGtiZndKySFhgkesF8R7WIO8pp1")).willThrow(new FirebaseException());
        BDDMockito.given(emailService.sendEmail("songsangmiffy@gmail.com","ZjGtiZndKySFhgkesF8R7WIO8pp1")).willReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.get("/account/send/email/{param}","0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")
                //.content(mapper.writeValueAsString(account))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(status().is4xxClientError());
               // .andExpect(content().string(String.valueOf(false)));
        //.andExpect(content().string("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D"));
        // .andExpect((ResultMatcher) jsonPath("$[0].name", is("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")));
    }*/
    @Test
    public void testSendEmail_cannotSendData() throws Exception {
        BDDMockito.given(aesService.decrypt("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")).willReturn("ZjGtiZndKySFhgkesF8R7WIO8pp1");
        BDDMockito.given(accountService.getEmailByUID("ZjGtiZndKySFhgkesF8R7WIO8pp1")).willReturn("songsangmiffy@gmail.com");
        BDDMockito.given(emailService.sendEmail("songsangmiffy@gmail.com","ZjGtiZndKySFhgkesF8R7WIO8pp1")).willReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.get("/account/send/email/{param}","0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")
                //.content(mapper.writeValueAsString(account))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(false)));
        //.andExpect(content().string("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D"));
        // .andExpect((ResultMatcher) jsonPath("$[0].name", is("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")));
    }

    @Test
    public void testUpdateStatusByVerifyPhone_canUpdateData() throws Exception {
        Account account = new Account("ZjGtiZndKySFhgkesF8R7WIO8pp1",
                null,
                null,
                null, null,
                null, null,
                null, null,
                null, null);

        BDDMockito.given(aesService.decrypt("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")).willReturn("ZjGtiZndKySFhgkesF8R7WIO8pp1");
        BDDMockito.given(accountService.updateStatus(account,"verified")).willReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.get("/account/update/status/{param}","0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")
                //.content(mapper.writeValueAsString(account))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(true)));
        //.andExpect(content().string("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D"));
        // .andExpect((ResultMatcher) jsonPath("$[0].name", is("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")));
    }

    @Test
    public void testUpdateStatusByVerifyPhone_cannotUpdateData() throws Exception {
        Account account = new Account("ZjGtiZndKySFhgkesF8R7WIO8pp1",
                null,
                null,
                null, null,
                null, null,
                null, null,
                null, null);

        BDDMockito.given(aesService.decrypt("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")).willReturn("ZjGtiZndKySFhgkesF8R7WIO8pp1");
        BDDMockito.given(accountService.updateStatus(account,"verified")).willReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.get("/account/update/status/{param}","0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")
                //.content(mapper.writeValueAsString(account))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        //.andExpect(content().string("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D"));
        // .andExpect((ResultMatcher) jsonPath("$[0].name", is("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")));
    }

    @Test
    public void testUpdateStatusByVerifyPhone_Exception() throws Exception {
        Account account = new Account("ZjGtiZndKySFhgkesF8R7WIO8pp1",
                null,
                null,
                null, null,
                null, null,
                null, null,
                null, null);

        BDDMockito.given(aesService.decrypt("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")).willReturn("ZjGtiZndKySFhgkesF8R7WIO8pp1");
        BDDMockito.given(accountService.updateStatus(account,"verified")).willThrow(new InterruptedException());
        mockMvc.perform(MockMvcRequestBuilders.get("/account/update/status/{param}","0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")
                //.content(mapper.writeValueAsString(account))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        //.andExpect(content().string("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D"));
        // .andExpect((ResultMatcher) jsonPath("$[0].name", is("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")));
    }

    @Test
    public void testCheckDuplicatedPhonenumber_isDuplicated() throws Exception {

        BDDMockito.given(accountService.checkDuplicatedPhonenumber("+66929639169")).willReturn(true);
         mockMvc.perform(MockMvcRequestBuilders.get("/account/get/phonenumber/{phonenumber}","+66929639169")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(true)));
        //.andExpect(content().string("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D"));
        // .andExpect((ResultMatcher) jsonPath("$[0].name", is("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")));
    }

    @Test
    public void testCheckDuplicatedPhonenumber_isNotDuplicated() throws Exception {

        BDDMockito.given(accountService.checkDuplicatedPhonenumber("+66929636666")).willReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.get("/account/get/phonenumber/{phonenumber}","+66929636666")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(false)));
        //.andExpect(content().string("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D"));
        // .andExpect((ResultMatcher) jsonPath("$[0].name", is("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")));
    }

    @Test
    public void testCheckDuplicatedPhonenumber_Exception() throws Exception {

        BDDMockito.given(accountService.checkDuplicatedPhonenumber("+66929636666")).willThrow(new InterruptedException());
        mockMvc.perform(MockMvcRequestBuilders.get("/account/get/phonenumber/{phonenumber}","+66929636666")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        //.andExpect(content().string("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D"));
        // .andExpect((ResultMatcher) jsonPath("$[0].name", is("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")));
    }

    @Test
    public void testCheckDuplicatedStudentId_Exception() throws Exception {

        BDDMockito.given(accountService.checkDuplicatedStudentId("582115000")).willThrow(new InterruptedException());
        mockMvc.perform(MockMvcRequestBuilders.get("/account/get/studentId/{studentId}","582115000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        //.andExpect(content().string("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D"));
        // .andExpect((ResultMatcher) jsonPath("$[0].name", is("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")));
    }

    @Test
    public void testCheckDuplicatedStudentId_isDuplicated() throws Exception {

        BDDMockito.given(accountService.checkDuplicatedStudentId("582115019")).willReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.get("/account/get/studentId/{studentId}","582115019")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(true)));
        //.andExpect(content().string("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D"));
        // .andExpect((ResultMatcher) jsonPath("$[0].name", is("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")));
    }

    @Test
    public void testCheckDuplicatedStudentId_isnotDuplicated() throws Exception {

        BDDMockito.given(accountService.checkDuplicatedStudentId("582115050")).willReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.get("/account/get/studentId/{studentId}","582115050")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(false)));
        //.andExpect(content().string("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D"));
        // .andExpect((ResultMatcher) jsonPath("$[0].name", is("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")));
    }

    @Test
    public void testUpdateStatusAccountByConfirmEmail_Exception() throws Exception{
        Account account = new Account("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D",
                null,
                "songsangmiffy@gmail.com",
                null, null,
                null, null,
                null, null,
                null, null);
        Account result = new Account(null,
                null,
                "songsangmiffy@gmail.com",
                null, null,
                null, null,
                null, null,
                null, null);
        BDDMockito.given(aesService.decrypt("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")).willReturn("ZjGtiZndKySFhgkesF8R7WIO8pp1");
        BDDMockito.given(aesService.decrypt("VI7LM2n7A%2FiYaxBnDnZYsDZj3roVQl1CkS87bNeEqe4%3D")).willReturn("2018-8-7T24.00.00.000");
        BDDMockito.given(accountService.getEmailByUID("ZjGtiZndKySFhgkesF8R7WIO8pp1")).willReturn("songsangmiffy@gmail.com");
        BDDMockito.given(accountService.updateStatus(account,"activated")).willReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.get("/account/update/status")
                .content("?id=0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D&time=VI7LM2n7A%2FiYaxBnDnZYsDZj3roVQl1CkS87bNeEqe4%3D")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
                //.andExpect(content().string(String.valueOf(result)));
    }

    /*@Test
    public void testUpdateStatusAccountByConfirmEmail_canUpdate() throws Exception{
        Map<String,String> map = new HashMap<String,String>();
        map.put("id", "0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D");
        map.put("time","VI7LM2n7A%2FiYaxBnDnZYsDZj3roVQl1CkS87bNeEqe4%3D");
        ObjectMapper mapper = new ObjectMapper();
        Account account = new Account("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D",
                null,
                "songsangmiffy@gmail.com",
                null, null,
                null, null,
                null, null,
                null, null);
        Account result = new Account(null,
                null,
                "songsangmiffy@gmail.com",
                null, null,
                null, null,
                null, null,
                null, null);
        BDDMockito.given(aesService.decrypt("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")).willReturn("ZjGtiZndKySFhgkesF8R7WIO8pp1");
        BDDMockito.given(aesService.decrypt("VI7LM2n7A%2FiYaxBnDnZYsDZj3roVQl1CkS87bNeEqe4%3D")).willReturn("2018-8-7T24.00.00.000");
        BDDMockito.given(accountService.getEmailByUID("ZjGtiZndKySFhgkesF8R7WIO8pp1")).willReturn("songsangmiffy@gmail.com");
        BDDMockito.given(accountService.updateStatus(account,"activated")).willReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.get("/account/update/status")
                .content(mapper.writeValueAsString(map))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().);
        //.andExpect(content().string(String.valueOf(result)));
    }*/

    /*@Test
    public void testUpdateStatusAccountByConfirmEmail_cannotUpdate() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D");
        map.put("time", "VI7LM2n7A%2FiYaxBnDnZYsDZj3roVQl1CkS87bNeEqe4%3D");
        ObjectMapper mapper = new ObjectMapper();
        Account account = new Account("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D",
                null,
                "songsangmiffy@gmail.com",
                null, null,
                null, null,
                null, null,
                null, null);
        Account result = new Account(null,
                null,
                "songsangmiffy@gmail.com",
                null, null,
                null, null,
                null, null,
                null, null);
        BDDMockito.given(aesService.decrypt("0IJ%2B0zK%2F7HLmBJygD80WruHpEVbty6AEcjKqvbjOW6o%3D")).willReturn("ZjGtiZndKySFhgkesF8R7WIO8pp1");
        BDDMockito.given(aesService.decrypt("VI7LM2n7A%2FiYaxBnDnZYsDZj3roVQl1CkS87bNeEqe4%3D")).willReturn("2018-8-6T24.00.00.000");
        BDDMockito.given(accountService.getEmailByUID("ZjGtiZndKySFhgkesF8R7WIO8pp1")).willReturn("songsangmiffy@gmail.com");
        BDDMockito.given(accountService.updateStatus(account, "activated")).willReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.get("/account/update/status")
                .content(mapper.writeValueAsString(map))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }*/
}
