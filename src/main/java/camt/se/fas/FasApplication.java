package camt.se.fas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ComponentScan({"camt.se.fas.dao", "camt.se.fas.controller", "camt.se.fas.service", "camt.se.fas.config"})
public class FasApplication {

    public static void main(String[] args) {
        SpringApplication.run(FasApplication.class, args);

    }
}
