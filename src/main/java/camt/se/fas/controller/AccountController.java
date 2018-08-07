package camt.se.fas.controller;

import camt.se.fas.entity.Account;
import camt.se.fas.service.AESService;
import camt.se.fas.service.AccountService;
import camt.se.fas.service.EmailService;
import camt.se.fas.service.SMSService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.nexmo.client.NexmoClientException;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

@CrossOrigin
@RestController("/account")
public class AccountController {
    Logger LOGGER = LoggerFactory.getLogger(AccountController.class.getName());
    AccountService accountService;
    EmailService emailService;
    SMSService smsService;
    AESService aes;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
    @Autowired
    public void setEmailService(EmailService emailService){
        this.emailService = emailService;
    }
    @Autowired
    public void setSmsService(SMSService smsService){
        this.smsService = smsService;
    }
    @Autowired
    public void setAesService(AESService aesService){
        this.aes = aesService;
    }

    @PostMapping("/account/create")
    public ResponseEntity uploadAccount(@RequestBody Account account) {
        try {
            String uid = accountService.registerAccount(account);
            /*AES aes = new AES();*/
            LOGGER.info("Return account:" + aes.encrypt(uid));
            return ResponseEntity.ok(aes.encrypt(uid));
        }  catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("account/send/phonenumber/{param}")
    public ResponseEntity sendPhonenumber (@PathVariable("param")String param){
        int otp = ThreadLocalRandom.current().nextInt(100000, 900000);
        String refCode = RandomStringUtils.randomAlphabetic(6);
        try {
        /*AES aes = new AES();*/
        String uid = aes.decrypt(param);
        LOGGER.info("UID "+uid);
        String phonenumber = accountService.getPhonenumberByUID(uid);
            smsService.sendSMS(phonenumber,refCode,otp);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("otp",String.valueOf(otp));
            responseHeaders.set("refCode",String.valueOf(refCode));
            responseHeaders.set("phonenumber",String.valueOf(phonenumber));
            return ResponseEntity.ok(responseHeaders);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }


    }
    @PostMapping("/account/update/{param}")
    public ResponseEntity updateAccount(@RequestBody Account account,@PathVariable("param")String param) {
        try {
            LOGGER.info(param);
            /*AES aes = new AES();*/
            String uid = aes.decrypt(param);
            LOGGER.info("update "+uid);
            account.setUid(uid);
            boolean result = accountService.registerAccountInfo(account);
            if(result){
                return ResponseEntity.ok(true);
            }else{
                return ResponseEntity.ok(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("account/send/email/{param}")
    public ResponseEntity sendEmail (@PathVariable("param")String param){
        try {
            /*AES aes = new AES();*/
            String uid = aes.decrypt(param);
            LOGGER.info("UID "+uid);
            String email = accountService.getEmailByUID(uid);
            LOGGER.info("Email "+email);
            boolean result= emailService.sendEmail(email,uid);
            if(result){
                return ResponseEntity.ok(true);
            }else{
                return ResponseEntity.ok(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }

    @GetMapping("account/update/status/{param}")
    public ResponseEntity updateStatusByVerifyPhone (@PathVariable("param")String param){
        try {

            LOGGER.info("Original Key: " +param);
            String decodeKey = aes.decrypt(param);
            LOGGER.info("Encoded Key: " + decodeKey);
            Account account = new Account();
            account.setUid(decodeKey);
            boolean result = accountService.updateStatus(account,"verified");
            if(result){
                return ResponseEntity.ok(true);
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @RequestMapping(value="account/update/status", method=RequestMethod.GET )
    /*public ResponseEntity updateStatusAccountByConfirmEmail2(@PathVariable("key")String key, @PathVariable("localtime")String localtime)*/
    public ResponseEntity updateStatusAccountByConfirmEmail(@RequestParam Map<String, String> param){
        String id = param.get("id");
        String time = param.get("time");
        try {
            /*AES aes = new AES();*/
            LOGGER.info("Encoded Key: " + URLEncoder.encode(id, "UTF-8"));
            String decodeKey = aes.decrypt(URLEncoder.encode(id, "UTF-8"));
            LOGGER.info("Decoded Key: " + decodeKey);
            //Provide for encrypt Time
            LOGGER.info("Encoded Key: " + URLEncoder.encode(time, "UTF-8"));
            String decodeTime = aes.decrypt(URLEncoder.encode(time, "UTF-8"));
            LOGGER.info("Decoded Time: " + decodeTime);

            String email = accountService.getEmailByUID(decodeKey);
            LOGGER.info("Return "+email);
            LocalDateTime _localtime = LocalDateTime.parse(decodeTime);
            LOGGER.info("_localtime "+_localtime);
            System.out.println("Result: "+LocalDateTime.now().isBefore(_localtime.plusDays(1))+" Origin: "+_localtime+" Now: "+LocalDateTime.now() + " Deadline: "+_localtime.plusDays(1));
            if(LocalDateTime.now().isBefore(_localtime.plusDays(1))){
                Account account = new Account();
                account.setUid(decodeKey);
                account.setEmail(email);
                accountService.updateStatus(account,"activated");
                Account _account = new Account();
                _account.setEmail(account.getEmail());
                return ResponseEntity.ok(_account);
            }else{
                return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();
            }
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("account/get/phonenumber/{phonenumber}")
    public ResponseEntity checkDuplicatedPhonenumber (@PathVariable("phonenumber")String phonenumber){
        try {
            boolean result = accountService.checkDuplicatedPhonenumber(phonenumber);
            if(result){
                return ResponseEntity.ok(true);
            }else{
                return ResponseEntity.ok(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("account/get/studentId/{studentId}")
    public ResponseEntity checkDuplicatedStudentId (@PathVariable("studentId")String studentId){
        try {
            boolean result = accountService.checkDuplicatedStudentId(studentId);
            if(result){
                return ResponseEntity.ok(true);
            }else{
                return ResponseEntity.ok(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }



}
