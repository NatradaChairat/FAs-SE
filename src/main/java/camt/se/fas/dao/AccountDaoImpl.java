package camt.se.fas.dao;

import camt.se.fas.entity.Account;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile("DBDataSource")
@ConfigurationProperties(prefix = "server")
@Repository
public class AccountDaoImpl implements AccountDao {
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
