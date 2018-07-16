package camt.se.fas.dao;

import camt.se.fas.config.FirebaseConfig;
import camt.se.fas.entity.Account;
import com.google.firebase.database.DatabaseReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDaoImpl implements AccountDao {

    DatabaseReference databaseReference;
    @Autowired
    public void setDatabaseReference (FirebaseConfig firebaseConfig){
        this.databaseReference = firebaseConfig.firebaseDatabase();
    }

    @Override
    public Account save() {
        return null;
    }

    @Override
    public Account update() {
        return null;
    }

    @Override
    public Account findByUsername(String username){
//        System.out.println("Test Firebase: "+firebaseConfig.firebaseDatabase().getDatabase());
        String result = databaseReference.child("account_table").getKey();
        //        System.out.println("Test Dao: "+result);
        Account account = new Account();
        account.setFirstname(result);
        return account;
    }




}
