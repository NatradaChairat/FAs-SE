package camt.se.fas.dao;

import camt.se.fas.entity.Account;

public interface AccountDao{
     Account save();
     Account update();
     Account findByUsername(String username);

}
