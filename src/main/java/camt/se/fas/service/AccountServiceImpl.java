package camt.se.fas.service;

import camt.se.fas.dao.AccountDao;
import camt.se.fas.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.List;

@ConfigurationProperties(prefix = "server")
@Service
public class AccountServiceImpl implements AccountService {
    String imageBaseUrl;
    String baseUrl;
    String imageUrl;
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Autowired
    AccountDao accountDao;

    @Override
    public Account addAccount(Account account) {
        return accountDao.addAccount(account);
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
