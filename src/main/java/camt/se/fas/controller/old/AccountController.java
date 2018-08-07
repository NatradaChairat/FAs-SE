package camt.se.fas.controller.old;

import camt.se.fas.entity.Account;

import camt.se.fas.service.AESService;
import camt.se.fas.service.old.AccountService;
import camt.se.fas.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/*@CrossOrigin
@RestController("/account")*/
//@RequestMapping({"/account"})
public class AccountController {
    Logger LOGGER = LoggerFactory.getLogger(AccountController.class.getName());
    AccountService accountService;
    EmailService emailService;
    AESService aes;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
    @Autowired
    public void setEmailService(EmailService emailService){this.emailService = emailService;}
    @Autowired
    public void setAesService(AESService aesService){
        this.aes = aesService;
    }

    @PostMapping("/account/create")
    public ResponseEntity<?> uploadAccount(@RequestBody Account account) {
        System.out.println("Post Account working .. "+ account.toString());
        //boolean result = accountService.addAccountOfRegisterStepOne(account);
        Account _account = accountService.addAccountOfRegistrationStep1(account);
        LOGGER.info("Return account:"+_account);
        if(true/*_account.getAccountId() != null*/) {
            //emailService.sendEmail(_account);
            return new ResponseEntity<>(account, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/account/update")
    public ResponseEntity<?> updateAccount(@RequestBody Account account) {
        System.out.println("Post Account working .. "+ account.toString());
        Account _account = accountService.updateAccountOfRegistrationStep2(account);
        LOGGER.info("Return account:"+_account);
        if(true/*_account.getAccountId() != null*/) {
            return new ResponseEntity<>(account, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/account/resend/email/{key}")
    public ResponseEntity<?> reSendEmail(@PathVariable("key")String key) {
        //AES aes = new AES();
        String decodeKey = aes.decrypt(key);
        LOGGER.info("RE Decoded Key: "+decodeKey);
        Account account =  accountService.getAccount(decodeKey);
        if(true/*account.getAccountId() != null*/) {
            //emailService.sendEmail(account);
            return new ResponseEntity<>(account, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        }
    }

    //maybe changes to request method
    @GetMapping("account/get/username/{username}")
    public ResponseEntity getAccountByUsername (@PathVariable("username")String username){
        LOGGER.info("GET Account from Username working");
        //String result = accountService.findAccountByUsername(username);
        Account account = accountService.findAccountByUsername(username);
        LOGGER.info("Return account:"+account);
        if(account!=null){
            LOGGER.info("Result: Username is repeated");
            return ResponseEntity.ok(account);/*ResponseEntity.status(HttpStatus.NO_CONTENT).build();*/
        }else{
            LOGGER.info("Result: Username is not repeated");
            return /*ResponseEntity.ok(result);*/ ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("account/get/email/{email}")
    public ResponseEntity getAccountByEmail (@PathVariable("email")String email){
        LOGGER.info("GET Account from Email working");
        Account account = accountService.findAccountByEmail(email.toLowerCase());
        LOGGER.info("Return account:"+account);
        if(account!=null){
            LOGGER.info("Result: Email is repeated");
            return ResponseEntity.ok(account);/*ResponseEntity.status(HttpStatus.NO_CONTENT).build();*/
        }else{
            LOGGER.info("Result: Email is not repeated");
            return /*ResponseEntity.ok(result);*/ ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("account/get/studentId/{studentId}")
    public ResponseEntity getAccountByStudentId (@PathVariable("studentId")String studentId){
        LOGGER.info("GET Account from StudentId working");
        Account account = accountService.findAccountByStudentId(studentId);
        LOGGER.info("Return account:"+account);
        if(account!=null){
            LOGGER.info("Result: StudentId is repeated");
            return ResponseEntity.ok(account);
        }else{
            LOGGER.info("Result: StudentId is not repeated");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("account/get/phonenumber/{phonenumber}")
    public ResponseEntity getAccountByPhonenumber (@PathVariable("phonenumber")String phonenumber){
        LOGGER.info("GET Account from Phonenumber working");
        Account account = accountService.findAccountByPhonenumber(phonenumber);
        LOGGER.info("Return account:"+account);
        if(account!=null){
            LOGGER.info("Result: Phonenumber is repeated");
            return ResponseEntity.ok(account);
        }else{
            LOGGER.info("Result: Phonenumber is not repeated");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }


    /*@GetMapping("account/get/status/{email}/{username}/{localtime}")
    public ResponseEntity updateStatusAccountByConfirmEmail(@PathVariable("email")String email, @PathVariable("username")String username, @PathVariable("localtime")String localtime){
        System.out.println("GET Account working .."+username+" "+localtime);
        //String result = accountService.findStatusByAccountId(accountId);
        LocalDateTime _localtime = LocalDateTime.parse(localtime);
        if(LocalDateTime.now().isBefore(
                _localtime.plusMinutes(12))){
            System.out.println("Result: "+LocalDateTime.now().isBefore(_localtime.plusMinutes(20))+" Now: "+LocalDateTime.now() + " Deadline: "+_localtime.plusMinutes(20));
            //String result= accountService.findAccountIdByUsername(username);
            Boolean result = accountService.updateStatusByEmail(email,"activated");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(result != null){
                System.out.println("GET account Id: "+result);
                return ResponseEntity.ok(result);
            }else{
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        }else{
            emailService.sendEmail(email,username);
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();
        }
    }
*/
    /*@GetMapping("account/get/status/{key}")
    public ResponseEntity getAccountByParam(@PathVariable("key")String key){
        LOGGER.info("Encoded Key: "+key);
        AES aes = new AES();
        String decodeKey = aes.decrypt(key);
        LOGGER.info("Decoded Key: "+decodeKey);
        Account account =  accountService.getAccount(decodeKey);
        LOGGER.info("Return Account "+account);
        if(account != null) {
            return ResponseEntity.ok(account);
        }else return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }*/

    /*@GetMapping(value="account/get/status/{key}/{localtime}", produces = "text/plain;charset=UTF-8")*/
    @GetMapping(value="account/get/status/{key}/{starttime}")
    /*public ResponseEntity updateStatusAccountByConfirmEmail2(@PathVariable("key")String key, @PathVariable("localtime")String localtime)*/
    public ResponseEntity updateStatusAccountByConfirmEmail(@PathVariable("key")String key, @PathVariable("starttime")String starttime){
        LOGGER.info("Encoded Key: "+key);
        //AES aes = new AES();
        String decodeKey = aes.decrypt(key);
        LOGGER.info("Decoded Key: "+decodeKey);
        //Provide for encrypt Time
        /*String decodeTime = aes.decrypt(localtime);
        LOGGER.info("Decoded Time: "+decodeTime);*/
        Account account =  accountService.getAccount(decodeKey);

        LOGGER.info("Return "+account);
        LocalDateTime _localtime = LocalDateTime.parse(starttime);
        LOGGER.info("_localtime "+_localtime);
        System.out.println("Result: "+LocalDateTime.now().isBefore(_localtime.plusMinutes(12))+" Origin: "+_localtime+" Now: "+LocalDateTime.now() + " Deadline: "+_localtime.plusMinutes(15));
        if(LocalDateTime.now().isBefore(
                _localtime.plusMinutes(12))){
            if(account != null){
                /*Account _account = accountService.updateStatus(account, "activated");
                _account.setEmail(account.getEmail());
                _account.setUsername(account.getUsername());
                LOGGER.info("Final Account "+_account);
                return ResponseEntity.ok(_account);*/
                return null;
            }else{
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        }else{
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();
        }
    }






   /* @GetMapping("account/test")
    public ResponseEntity testDao (){
         Account account = accountService.testDao();
        if (account != null)
            return ResponseEntity.ok(account);

        else
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
*/



}
