package camt.se.fas.controller;


import camt.se.fas.entity.Account;
import camt.se.fas.service.PythonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController("/auth")
public class AuthenticationController {
    Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class.getName());
    PythonService pythonService;

    @Autowired
    public void setPythonService(PythonService pythonService){
        this.pythonService = pythonService;
    }

    @PostMapping("/auth/faceLogin")
    public ResponseEntity faceLogin(@RequestParam String imageUrl) {
        try {

            pythonService.runScript(imageUrl);
            return ResponseEntity.ok().build();
        }  catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
