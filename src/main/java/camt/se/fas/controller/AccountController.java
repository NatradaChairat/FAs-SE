package camt.se.fas.controller;

import camt.se.fas.entity.Account;
import camt.se.fas.service.AccountService;
import camt.se.fas.service.EmailService;
import camt.se.fas.service.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.UUID;

@CrossOrigin
@RestController("/account")
//@RequestMapping({"/account"})
public class AccountController {
    AccountService accountService;
    EmailService emailService;

    @Autowired
    public void setAccountService(AccountService accountService){
        this.accountService = accountService;
    }
    @Autowired
    public void setEmailService(EmailService emailService){this.emailService = emailService;}

    /*@Autowired
    public void setFirebaseService(FirebaseService firebaseService){ this.firebaseService = firebaseService;}*/

    /*@PostMapping("/create")
    public ResponseEntity<?> uploadAccount(@RequestBody Account account){
        //System.out.println("/account is working "+ account.getEmail());
        System.out.println(account);
        //accountService.addAccount(account);
        return ResponseEntity.ok(account);
    }*/
    @PostMapping("/account/create")
    public ResponseEntity<Account> postAccount(@RequestBody Account account){
        System.out.println("Post Account working .."+ account.getEmail() + "...");
        accountService.addAccount(account);
        emailService.sendEmail(account);
        //Sending Email
        /*String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = { account.getEmail() }; // list of recipient email addresses
        String subject = "Facial Authentication: Verification email";
        String body = "Email Address: "+ account.getEmail()+"\nUsername: "+account.getUsername()+
                "\nClick the link to verify email: Http://localhost:4200/comfirmedemail"*//*+account.getAccountId()*//*;*/
        //sendFromGMail(from, pass, to, subject, body);


        return new ResponseEntity<>(account, HttpStatus.OK);
    }



    @GetMapping("/get")
    public String getTest(){
        System.out.println("Test GET");
        return "Sucess TEST";
    }



}
