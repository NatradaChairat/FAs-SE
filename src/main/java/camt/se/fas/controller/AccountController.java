package camt.se.fas.controller;

import camt.se.fas.entity.Account;
import camt.se.fas.service.AccountService;
import camt.se.fas.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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

    @PostMapping("/account/create")
    public ResponseEntity<Account> uploadAccount(@RequestBody Account account) {
        System.out.println("Post Account working .. "+ account.getEmail());
        accountService.addAccountOfRegisterStepOne(account);
        emailService.sendEmail(account);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }


    //maybe changes to request method
    @GetMapping("account/get/username/{username}")
    public ResponseEntity getRepeatUsername (@PathVariable("username")String username){
        System.out.println("GET Account working ..");
        String result = accountService.findAccountByUsername(username);
        if(result!=null){
            System.out.println("username is repeated");
            return ResponseEntity.ok(result);/*ResponseEntity.status(HttpStatus.NO_CONTENT).build();*/
        }else{
            System.out.println("username is not repeated");
            return /*ResponseEntity.ok(result);*/ ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("account/get/email/{email}")
    public ResponseEntity getRepeatEmail (@PathVariable("email")String email){
        System.out.println("GET Account working ..");
        String result = accountService.findAccountByEmail(email.toLowerCase());
        if(result!=null){
            System.out.println("email is repeated");
            return ResponseEntity.ok(result);/*ResponseEntity.status(HttpStatus.NO_CONTENT).build();*/
        }else{
            System.out.println("email is not repeated");
            return /*ResponseEntity.ok(result);*/ ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("account/get/status/{email}/{username}/{localtime}")
    public ResponseEntity getAccountByEmail(@PathVariable("email")String email, @PathVariable("username")String username, @PathVariable("localtime")String localtime){
        System.out.println("GET Account working .."+username+" "+localtime);
        //String result = accountService.findStatusByAccountId(accountId);
        LocalDateTime _localtime = LocalDateTime.parse(localtime);
        if(LocalDateTime.now().isBefore(_localtime.plusMinutes(20))){
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
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();
        }
    }





}
