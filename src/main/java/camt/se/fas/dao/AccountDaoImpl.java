package camt.se.fas.dao;

import camt.se.fas.entity.Account;
import camt.se.fas.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile("DBDataSource")
@Repository
public class AccountDaoImpl implements AccountDao {
    /*AccountRepository accountRepository;
    @Autowired
    public void setAccountRepository(AccountRepository accountRepository){
        this.accountRepository =  accountRepository;
    }*/

    @Override
    public Account addAccount(Account account) {
        return null;/*accountRepository.save(account);*/
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
