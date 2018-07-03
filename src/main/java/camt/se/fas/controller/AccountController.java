package camt.se.fas.controller;

import camt.se.fas.entity.Account;
import camt.se.fas.service.AccountService;
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

    private static String USER_NAME = "camt.se.facialauthentication";  // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = "Fas_1234"; // GMail password
    private static String RECIPIENT = "songsangmiffy@gmail.com";

    @Autowired
    public void setAccountService(AccountService accountService){
        this.accountService = accountService;
    }

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

        //Sending Email

        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = { account.getEmail() }; // list of recipient email addresses
        String subject = "Facial Authentication: Verification email";
        String body = "Email Address: "+ account.getEmail()+"\nUsername: "+account.getUsername()+
                "\nHttp://localhost:4200/homepage";

        sendFromGMail(from, pass, to, subject, body);


        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props, null);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }

    @GetMapping("/get")
    public String getTest(){
        System.out.println("Test GET");
        return "Sucess TEST";
    }



}
