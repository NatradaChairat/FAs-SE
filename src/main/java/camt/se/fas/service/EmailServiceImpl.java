package camt.se.fas.service;

import camt.se.fas.entity.Account;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {
    private static String USER_NAME = "camt.se.facialauthentication";  // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = "Fas_1234"; // GMail password

   /* @Override
    public Boolean sendEmail(String email, String username) {
        String[] receiver = { email }; // list of recipient email addresses
        String subject = "Facial Authentication: Verification email";
        String encodeKey = null;
        try {
            AES aes = new AES();
            encodeKey = aes.encrypt(email);
            System.out.println("DecodedKey: "+encodeKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String params = ""+email+"/"+username+"/"+LocalDateTime.now();
        String secretParam = ""+encodeKey+"/"+LocalDateTime.now();
        System.out.println("Setting param: "+secretParam*//*params*//*);
        String body = "Email Address: "+ email+"\nUsername: "+username+
                "\nClick the link to verify email: Http://localhost:4200/confirmedemail/"+*//*params*//*secretParam;

        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", USER_NAME);
        props.put("mail.smtp.password", PASSWORD);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props, null);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(USER_NAME));
            InternetAddress[] toAddress = new InternetAddress[receiver.length];
            // To get the array of addresses
            for( int i = 0; i < receiver.length; i++ ) {
                toAddress[i] = new InternetAddress(receiver[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, USER_NAME, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            System.out.println("Sending success");
            transport.close();
            return true;
        }
        catch (AddressException ae) {
            ae.printStackTrace();
            return false;
        }
        catch (MessagingException me) {
            me.printStackTrace();
            return false;
        }

    }*/

    /*@Override
    public Boolean sendEmail(Account account) {
        String[] receiver = {account.getEmail()}; // list of recipient email addresses
        String subject = "Facial Authentication: Verification email";
        String encodeKey = null;
        String encodeLacaltime = null;
        try {
            AES aes = new AES();
            encodeKey = aes.encrypt(account.getAccountId());
            System.out.println("DecodedKey: "+encodeKey);
           *//* encodeLacaltime = aes.encrypt(LocalDateTime.now().toString());
            System.out.println("DecodedLocalTime: "+encodeLacaltime);*//*

        } catch (Exception e) {
            e.printStackTrace();
        }
        String secretParam = ""+encodeKey+"/"+LocalDateTime.now();
        System.out.println("Setting param: "+secretParam*//*params*//*);
        String body = "Email Address: "+ account.getEmail()+"\nUsername: "+account.getUsername()+
                "\nClick the link to verify email: http://localhost:4200/confirmedemail/"+*//*params*//*secretParam;

        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", USER_NAME);
        props.put("mail.smtp.password", PASSWORD);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props, null);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(USER_NAME));
            InternetAddress[] toAddress = new InternetAddress[receiver.length];
            // To get the array of addresses
            for( int i = 0; i < receiver.length; i++ ) {
                toAddress[i] = new InternetAddress(receiver[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, USER_NAME, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            System.out.println("Sending success");
            transport.close();
            return true;
        }
        catch (AddressException ae) {
            ae.printStackTrace();
            return false;
        }
        catch (MessagingException me) {
            me.printStackTrace();
            return false;
        }
    }*/

    @Override
    public Boolean sendEmail(String email, String uid) {
        String[] receiver = {email}; // list of recipient email addresses
        String subject = "Facial Authentication: Verification email";
        String encodeKey = null;
        String encodeLocaltime = null;
        try {
            AES aes = new AES();
            encodeKey = aes.encrypt(uid);
            encodeLocaltime = aes.encrypt(LocalDateTime.now().toString());
           /* encodeLacaltime = aes.encrypt(LocalDateTime.now().toString());
            System.out.println("DecodedLocalTime: "+encodeLacaltime);*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        String secretParam = "data?id="+encodeKey+"&time="+encodeLocaltime;
        System.out.println("Setting param: "+secretParam/*params*/);
        String body = "Email Address: "+ email+
                "\nClick the link to verify email: http://localhost:4200/confirmedemail/"+/*params*/secretParam;

        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", USER_NAME);
        props.put("mail.smtp.password", PASSWORD);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props, null);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(USER_NAME));
            InternetAddress[] toAddress = new InternetAddress[receiver.length];
            // To get the array of addresses
            for( int i = 0; i < receiver.length; i++ ) {
                toAddress[i] = new InternetAddress(receiver[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, USER_NAME, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            System.out.println("Sending success");
            transport.close();
            return true;
        }
        catch (AddressException ae) {
            ae.printStackTrace();
            return false;
        }
        catch (MessagingException me) {
            me.printStackTrace();
            return false;
        }
    }


}
