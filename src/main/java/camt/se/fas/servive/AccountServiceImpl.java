package camt.se.fas.servive;

import camt.se.fas.entity.Account;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.List;

@ConfigurationProperties(prefix = "server")
@Service
public class AccountServiceImpl implements AccountService {
    @Override
    public Account addAccount(Account account) {
        return null;
    }

    @Override
    public Account findById(long id) {
        return null;
    }

    @Override
    public Account findByUsername(String username) {
        return null;
    }

    @Override
    public Account findByEmail(String email) {
        return null;
    }

    @Override
    public List<Account> getAccount() {
        return null;
    }
}
