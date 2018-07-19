package camt.se.fas.dao;

import camt.se.fas.config.FirebaseConfig;
import camt.se.fas.entity.Account;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.sun.org.apache.bcel.internal.generic.LOOKUPSWITCH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sun.rmi.runtime.Log;

import java.util.HashMap;
import java.util.Map;


@Repository
public class AccountDaoImpl implements AccountDao {
    Logger LOGGER = LoggerFactory.getLogger(AccountDaoImpl.class.getName());
    DatabaseReference databaseReference;

    @Autowired
    public void setDatabaseReference (FirebaseConfig firebaseConfig){
        this.databaseReference = firebaseConfig.firebaseDatabase();
    }


    @Override
    public String addUsernamePassword(Account account) {
        Account _account = new Account();
        LOGGER.info("Add Username, Password: "+account.getUsername()+", "+account.getPassword());
        DatabaseReference usersRef = databaseReference.child("account_table");
        Map<String, Object> accountTableMap = new HashMap<>();
        accountTableMap.put("username", account.getUsername());
        accountTableMap.put("password", account.getPassword());
        accountTableMap.put("studentId", account.getStudentId());
        usersRef.child(account.getAccountId()).setValueAsync(accountTableMap);
        //return usersRef.child(account.getAccountId()).getKey().toString();
        return usersRef.child(account.getAccountId()).getKey();
    }

    @Override
    public String addStatus(Account account,String status) {
        LOGGER.info("Add Status: "+account.getAccountId()+", "+status);
        DatabaseReference usersRef = databaseReference.child("account_status_table");
        Map<String, Object> accountTableMap = new HashMap<>();
        accountTableMap.put(account.getAccountId(), status);
        usersRef.updateChildrenAsync(accountTableMap);
        return usersRef.child(account.getAccountId()).getKey();
    }

    @Override
    public String addEmailPhonenumber(Account account) {
        LOGGER.info("Add Email, Phonenumber: "+account.getEmail()+ ", "+account.getPhonenumber());
        DatabaseReference usersRef = databaseReference.child("account_contact_table");
        Map<String, Object> accountTableMap = new HashMap<>();
        accountTableMap.put("email", account.getEmail().toLowerCase());
        accountTableMap.put("phonenumber", account.getPhonenumber());
        usersRef.child(account.getAccountId()).updateChildrenAsync(accountTableMap);
        //return usersRef.child(accountId).getKey().toString();
        return usersRef.child(account.getAccountId()).getKey();
    }

    @Override
    public Account findAccountByEmail(String email) {
        Account account = new Account();
        databaseReference.child("account_contact_table").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                LOGGER.info("findAccountByEmail: "+snapshot.toString());
                if(snapshot.getValue() != null) {
                    String result = snapshot.getValue().toString().substring(1, 8);
                    LOGGER.info(result);
                    account.setAccountId(result);
                    String _email = snapshot.child(result).child("email").getValue().toString();
                    LOGGER.info(_email);
                    account.setEmail(_email);
                    String _phonenumber = snapshot.child(result).child("phonenumber").getValue().toString();
                    LOGGER.info(_phonenumber);
                    account.setPhonenumber(_phonenumber);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }finally {
            LOGGER.info(account.toString());
            if(account.getAccountId()!=null) {
                return account;
            }else return null;
        }
    }

    @Override
    public Account findAccountByUsername(String username){
        Account account = new Account();
        try {
            databaseReference.child("account_table").orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    LOGGER.info("findAccountByUsername: "+snapshot.toString());
                    String result = snapshot.getValue().toString().substring(1, 8);
                    LOGGER.info(result);
                    account.setAccountId(result);

                    String _username = snapshot.child(result).child("username").getValue().toString();
                    LOGGER.info(_username);
                    account.setUsername(_username);
                    String _password = snapshot.child(result).child("password").getValue().toString();
                    LOGGER.info(_password);
                    account.setPassword(_password);
                    String _studentId = snapshot.child(result).child("studentId").getValue().toString();
                    LOGGER.info(_studentId);
                    account.setStudentId(_studentId);
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });


            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }finally {
            LOGGER.info(account.toString());
            if(account.getAccountId()!=null) {
                return account;
            }else return null;
        }

    }

    @Override
    public Account findLastAccountId(){
        Account account = new Account();
        databaseReference.child("account_table").limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                LOGGER.info(snapshot.toString());
                String keyOnlyNumber = snapshot.getValue().toString().substring(3, 8);
                LOGGER.info("Get KEY: " + keyOnlyNumber);
                int keyNumber = Integer.parseInt(keyOnlyNumber);
                LOGGER.info("Get KEY number: " + keyNumber);
                /*String newKey = "FA" + String.format("%05d", keyNumber + 1);
                LOGGER.info("Get Next KEY: " + newKey);*/
                account.setAccountId(snapshot.getValue().toString().substring(1,8));
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }finally {
            LOGGER.info(account.toString());
            return account;
        }
    }


}
