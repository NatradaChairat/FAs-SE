package camt.se.fas.controller;

import camt.se.fas.entity.Account;
import camt.se.fas.service.AccountService;
import camt.se.fas.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@CrossOrigin
@RestController("/account")
//@RequestMapping({"/account"})
public class AccountController {
    Logger LOGGER = LoggerFactory.getLogger(AccountController.class.getName());
    AccountService accountService;
    EmailService emailService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
    @Autowired
    public void setEmailService(EmailService emailService){this.emailService = emailService;}

    @PostMapping("/account/create")
    public ResponseEntity<?> uploadAccount(@RequestBody Account account) {
        System.out.println("Post Account working .. "+ account.getEmail());
        //boolean result = accountService.addAccountOfRegisterStepOne(account);
        Account _account = accountService.addOnlyAccount(account);
        LOGGER.info("Return account:"+account);
        if(_account.getAccountId() != null) {
            emailService.sendEmail(_account.getEmail(),_account.getUsername());
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

    @GetMapping("account/get/status/{email}/{username}/{localtime}")
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


    @GetMapping("account/test")
    public ResponseEntity testDao (){
         Account account = accountService.testDao();
        //Account account = null;

        System.out.println(account);
        if (account.getAccountId() != null)

            return ResponseEntity.ok(account);

        else

            //http code 204

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }




}
