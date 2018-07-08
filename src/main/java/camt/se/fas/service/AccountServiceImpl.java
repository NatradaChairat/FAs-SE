package camt.se.fas.service;

import camt.se.fas.entity.Account;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@ConfigurationProperties(prefix = "server")
@Service
public class AccountServiceImpl implements AccountService {

    @Value("${firebase.database-url}")
    String firebaseUrl;

    @Value("${firebase.config.path}")
    String firebaseConfigPath;

    DatabaseReference databaseReference;

    @Override
    public Boolean addAccountOfRegisterStepOne(Account account) {
        try {
            InputStream serviceAccount = AccountServiceImpl.class.getClassLoader().getResourceAsStream(firebaseConfigPath);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(firebaseUrl)
                    .build();
            if(FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
            databaseReference = FirebaseDatabase.getInstance().getReference();
            //ref last of object in account_table
            databaseReference.child("account_table").limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    System.out.println("Key: " + snapshot.getKey() + "value: " + snapshot.getValue());
                    String keyOnlyNumber = snapshot.getValue().toString().substring(3, 8);
                    System.out.println("Get KEY: " + keyOnlyNumber);
                    int keyNumber = Integer.parseInt(keyOnlyNumber);
                    System.out.println("Get KEY number: " + keyNumber);

                    String newKey = "FA" + String.format("%05d", keyNumber + 1);
                    System.out.println("Get KEY: " + newKey);
                    System.out.println("saveAccount is working | accountId " + newKey);

                    addUsernamePasswordToDB(newKey, account);
                    addStatusToDB(newKey,"registered");
                    addEmailPhonenumberToDB(newKey, account);
                }
                @Override
                public void onCancelled (DatabaseError error){ }
            });
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean addUsernamePasswordToDB(String accountId, Account account) {
        System.out.println("addUsernamePasswordToDB working ");
        DatabaseReference usersRef = databaseReference.child("account_table");
        Map<String, Object> accountTableMap = new HashMap<>();
        accountTableMap.put("username", account.getUsername());
        accountTableMap.put("password", account.getPassword());
        accountTableMap.put("studentId", account.getStudentId());
        usersRef.child(accountId).setValueAsync(accountTableMap);
        return null;
    }

    @Override
    public Boolean addStatusToDB(String accountId, String status) {
        System.out.println("addStatusToDB working ");
        DatabaseReference usersRef = databaseReference.child("account_status_table");
        Map<String, Object> accountTableMap = new HashMap<>();
        accountTableMap.put(accountId, status);
        usersRef.updateChildrenAsync(accountTableMap);
        return true;
    }

    @Override
    public Boolean addEmailPhonenumberToDB(String accountId, Account account) {
        System.out.println("addEmailPhonenumberToDB working ");
        DatabaseReference usersRef = databaseReference.child("account_contact_table");
        Map<String, Object> accountTableMap = new HashMap<>();
        accountTableMap.put("email", account.getEmail());
        accountTableMap.put("phonenumber", account.getPhonenumber());
        usersRef.child(accountId).updateChildrenAsync(accountTableMap);
        return null;
    }


    /*@Override
    public Account findByAccountId(String accountId) {
        try {
            InputStream serviceAccount = AccountServiceImpl.class.getClassLoader().getResourceAsStream(firebaseConfigPath);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(firebaseUrl)
                    .build();

            FirebaseApp.initializeApp(options);
            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("account_table").child(accountId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    Account account = snapshot.getValue(Account.class);
                    System.out.println(account);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    System.out.println("No account ID");
                }
            });

        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }*/

    private String result;
    @Override
    public String findAccountByUsername(String username) {
        try {
            CountDownLatch done = new CountDownLatch(1);
            InputStream serviceAccount = AccountServiceImpl.class.getClassLoader().getResourceAsStream(firebaseConfigPath);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(firebaseUrl)
                    .build();

            if(FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("account_table").orderByChild("username").equalTo(username).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    System.out.println("get: "+snapshot.getValue());
                    if(snapshot.getValue()!=null){
                        result = snapshot.getValue().toString();
                    }
                    else{ result=null; }
                    done.countDown();
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    System.out.println(error.getDetails());
                }
            });
            done.await();
            System.out.println("final result: "+result);
            return result;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String findAccountByEmail(String email) {
        try {
            CountDownLatch done = new CountDownLatch(1);
            InputStream serviceAccount = AccountServiceImpl.class.getClassLoader().getResourceAsStream(firebaseConfigPath);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(firebaseUrl)
                    .build();
            if(FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("account_contact_table").orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    System.out.println("get: "+snapshot.getValue());
                    if(snapshot.getValue()!=null){
                        result = snapshot.getValue().toString();
                        done.countDown();
                    }
                    else{
                        result=null;
                        done.countDown();
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    System.out.println(error.getDetails());
                }
            });
            done.await();
            System.out.println("final result: "+result);
            return result;

        }catch (IOException e){
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean updateStatusByEmail(String email,String status) {
        try {
            InputStream serviceAccount = AccountServiceImpl.class.getClassLoader().getResourceAsStream(firebaseConfigPath);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(firebaseUrl)
                    .build();
            if(FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("account_contact_table").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    System.out.println(snapshot.getValue());
                    String accountId = snapshot.getValue().toString().substring(1,8);
                    System.out.println("accountId: "+accountId);
                    addStatusToDB(accountId,status);
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
            return true;
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Account> getAccount() {
        return null;
    }

}
