package camt.se.fas;

import camt.se.fas.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FasApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FasApplicationTests {

    @Test
    public void contextLoads() {
    }

}
