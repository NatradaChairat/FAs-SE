package camt.se.fas.controller;

import camt.se.fas.entity.Account;
import camt.se.fas.service.*;
import org.apache.commons.lang3.RandomStringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Autowired
    public void setSmsService(SMSService smsService) {
        this.smsService = smsService;
    }

    @Autowired
    public void setAesService(AESService aesService) {
        this.aes = aesService;
    }

    @PostMapping("/account/create")//
    public ResponseEntity uploadAccount(@RequestBody Account account) {
        try {
            String uid = accountService.registerAccount(account);
            /*AES aes = new AES();*/
            LOGGER.info("Return account:" + aes.encrypt(uid));
            return ResponseEntity.ok(aes.encrypt(uid));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/account/update")//
    public ResponseEntity updateAccount(@RequestBody Account account/*,@PathVariable("param")String encryptUID*/) {
        LOGGER.info(account.toString());
        String encryptUID = account.getUid();
        try {
            LOGGER.info(encryptUID);
            /*AES aes = new AES();*/
            String uid = aes.decrypt(encryptUID);
            LOGGER.info("update " + uid);
            account.setUid(uid);
            boolean result = accountService.registerAccountInfo(account);
            if (result) {
                return ResponseEntity.ok(true);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                //return ResponseEntity.ok(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(value = "/account/upload/image")
    public ResponseEntity uploadImage(@RequestParam("imageUrl") String imageUrl) {
        if (imageUrl.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            //accountService.
        }
        return ResponseEntity.ok(null);
    }


    @GetMapping("account/send/phonenumber/{param}/{phonenumber}")
    public ResponseEntity sendPhonenumber(@PathVariable("param") String param, @PathVariable("phonenumber") String phonenumber) {
        int otp = ThreadLocalRandom.current().nextInt(100000, 900000);
        String refCode = RandomStringUtils.randomAlphabetic(6);
        LOGGER.info("UID " + param);
        try {
            /*AES aes = new AES();*/
            String uid = aes.decrypt(param);
            LOGGER.info("UID " + uid);
            smsService.sendSMS(phonenumber, refCode, otp);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("otp", String.valueOf(otp));
            responseHeaders.set("refCode", String.valueOf(refCode));
            responseHeaders.set("phonenumber", String.valueOf(phonenumber));
            return ResponseEntity.ok(responseHeaders);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("account/send/email/{param}")
    public ResponseEntity sendEmail(@PathVariable("param") String param) {
        try {
            /*AES aes = new AES();*/
            String uid = aes.decrypt(param);
            LOGGER.info("UID " + uid);
            String email = accountService.getEmailByUID(uid);
            LOGGER.info("Email " + email);
            boolean result = emailService.sendVerifyEmail(email, uid);
            if (result) {
                return ResponseEntity.ok(true);
            } else {
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }

    @GetMapping("account/send/email/{param}/{result}")
    public ResponseEntity sendResultAuthenProcessToEmail(@PathVariable("param") String param, @PathVariable("result") String result) {
        try {
            /*AES aes = new AES();*/
            String uid = aes.decrypt(param);
            LOGGER.info("UID " + uid);
            String email = accountService.getEmailByUID(uid);
            LOGGER.info("Email " + email);
            boolean resultSending = emailService.sendResultAuthenProcessEmail(email, result);
            if (resultSending) {
                return ResponseEntity.ok(true);
            } else {
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }

    @GetMapping("account/update/status/{param}")
    public ResponseEntity updateStatusByVerifyPhonenumber(@PathVariable("param") String param) {
        try {

            LOGGER.info("Original Key: " + param);
            String decodeKey = aes.decrypt(param);
            LOGGER.info("Encoded Key: " + decodeKey);
            Account account = new Account();
            account.setUid(decodeKey);
            account.setStatus("verified");
            //boolean result = accountService.updateStatus(account,"verified");
            boolean result = accountService.updateStatus(account);
            if (result) {
                return ResponseEntity.ok(true);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @RequestMapping(value = "account/update/status", method = RequestMethod.GET)
    public ResponseEntity updateStatusByVerifyEmail(@RequestParam Map<String, String> mapIdTime) {
        String id = mapIdTime.get("id");
        String time = mapIdTime.get("time");
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
            LOGGER.info("Return " + email);
            LocalDateTime _localtime = LocalDateTime.parse(decodeTime);
            LOGGER.info("_localtime " + _localtime);
            System.out.println("Result: " + LocalDateTime.now().isBefore(_localtime.plusDays(1)) + " Origin: " + _localtime + " Now: " + LocalDateTime.now() + " Deadline: " + _localtime.plusDays(1));
            if (LocalDateTime.now().isBefore(_localtime.plusDays(1))) {
                Account account = new Account();
                account.setUid(decodeKey);
                account.setEmail(email);
                account.setStatus("activated");
                accountService.updateStatus(account);
                Account _account = new Account();
                _account.setEmail(account.getEmail());
                return ResponseEntity.ok(_account);
            } else {
                return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("account/update/status/")
    public ResponseEntity updateStatus(@RequestBody Account account) {
        try {
            boolean result = accountService.updateStatus(account);
            if (account.getStatus().equalsIgnoreCase("approved")) {

            }
            LOGGER.info("Result " + result);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @GetMapping("account/get/phonenumber/{phonenumber}")
    public ResponseEntity checkDuplicatedPhonenumber(@PathVariable("phonenumber") String phonenumber) {
        try {
            boolean result = accountService.checkDuplicatedPhonenumber(phonenumber);
            if (result) {
                return ResponseEntity.ok(true);
            } else {
                return ResponseEntity.ok(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("account/get/studentId/{studentId}")
    public ResponseEntity checkDuplicatedStudentId(@PathVariable("studentId") String studentId) {
        try {
            boolean result = accountService.checkDuplicatedStudentId(studentId);
            if (result) {
                return ResponseEntity.ok(true);
            } else {
                return ResponseEntity.ok(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("account/get/account/{status}")
    public ResponseEntity getAccountByStatus(@PathVariable("status") String status) {
        try {
            List<Account> accounts = accountService.getAccountByStatus(status);
            return ResponseEntity.ok(accounts);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

    }

    @GetMapping("account/get/{id}")
    public ResponseEntity getAccountByUid(@PathVariable("id") String id) {
        try {
            LOGGER.info("Encoded Key: " + id);
            String decodeUID = aes.decrypt(id);
            LOGGER.info("Decoded Key: " + decodeUID);
            Account account = accountService.getAccountByUID(decodeUID);
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("account/get/randomtext/{id}")
    public ResponseEntity getRandomTextByUid(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

}
