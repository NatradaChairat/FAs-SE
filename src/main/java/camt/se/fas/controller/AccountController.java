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
    public ResponseEntity<Account> postAccount(@RequestBody Account account){
        System.out.println("Post Account working .."+ account.getEmail() + "...");
        accountService.addAccount(account);
        emailService.sendEmail(account);

        return new ResponseEntity<>(account, HttpStatus.OK);
    }



    @GetMapping("/get")
    public String getTest(){
        System.out.println("Test GET");
        return "Sucess TEST";
    }



}
