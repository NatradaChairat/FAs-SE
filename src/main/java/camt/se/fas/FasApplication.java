package camt.se.fas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"camt.se.fas.dao"})
public class FasApplication {

    public static void main(String[] args) {
        SpringApplication.run(FasApplication.class, args);
    }
}
