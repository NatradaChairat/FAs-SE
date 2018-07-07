package camt.se.fas.controller;

import camt.se.fas.entity.Account;
import camt.se.fas.service.AccountService;
import camt.se.fas.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Account> uploadAccount(@RequestBody Account account){
        System.out.println("Post Account working .. "+ account.getEmail());
        accountService.addAccount(account);
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
        String result = accountService.findAccountByEmail(email);
        if(result!=null){
            System.out.println("email is repeated");
            return ResponseEntity.ok(result);/*ResponseEntity.status(HttpStatus.NO_CONTENT).build();*/
        }else{
            System.out.println("email is not repeated");
            return /*ResponseEntity.ok(result);*/ ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }





}
