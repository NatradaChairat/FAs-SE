package camt.se.fas.controller;

import camt.se.fas.entity.Account;
import camt.se.fas.servive.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping({"/account"})
public class AccountController {
    AccountService accountService;
    @Autowired
    public void setAccountService(AccountService accountService){
        this.accountService = accountService;
    }


    /*@PostMapping("/create")
    public ResponseEntity<?> uploadAccount(@RequestBody Account account){
        //System.out.println("/account is working "+ account.getEmail());
        System.out.println(account);
        //accountService.addAccount(account);
        return ResponseEntity.ok(account);
    }*/
    @PostMapping("/create")
    public ResponseEntity<Account> postAccount(@RequestBody Account account){

        System.out.println("Post Account working .."+ account.getEmail() + "...");

        account.setId(UUID.randomUUID().timestamp());
        accountService.addAccount(account);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping("/get")
    public String getTest(){
        System.out.println("Test GET");
        return "Sucess TEST";
    }



}
