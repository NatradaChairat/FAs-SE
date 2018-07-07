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
import java.util.List;
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
    public Account addAccount(Account account) {
        try {
            //FileInputStream serviceAccount = new FileInputStream("D://FAs/FacialAuthentication-SE/src/main/resource/"+firebaseConfigPath);
            InputStream serviceAccount = AccountServiceImpl.class.getClassLoader().getResourceAsStream(firebaseConfigPath);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(firebaseUrl)
                    .build();
            if(FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("account").addChildEventListener(new ChildEventListener(){
                @Override
                public void onChildAdded(DataSnapshot snapshot, String previousChildName) {



                }
                @Override
                public void onChildChanged(DataSnapshot snapshot, String previousChildName) {

                }

                @Override
                public void onChildRemoved(DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot snapshot, String previousChildName) {

                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
            /*accountRepository.save(account);*/
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Account findById(long id) {
        try {
            InputStream serviceAccount = AccountServiceImpl.class.getClassLoader().getResourceAsStream(firebaseConfigPath);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(firebaseUrl)
                    .build();

            FirebaseApp.initializeApp(options);
            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("accounts").child("accountId").addListenerForSingleValueEvent(new ValueEventListener() {
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
    }

    @Override
    public Account findByUsername(String username) {
        return null;
    }

    /*private Account _account;
    @Override
    public Account findByUsername(String username) {
        try {
            CountDownLatch done = new CountDownLatch(1);
            InputStream serviceAccount = AccountDaoImpl.class.getClassLoader().getResourceAsStream(firebaseConfigPath);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(firebaseUrl)
                    .build();

            FirebaseApp.initializeApp(options);
            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("account_table").orderByChild("username").equalTo(username).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    //System.out.println(snapshot.getValue().toString());
                    _account = snapshot.getValue(Account.class);
                    System.out.println(" ** "+_account.toString());
                    done.countDown();
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    _account = null;
                }
            });
            done.await();
            return _account;

        }catch (IOException e){
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
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
                        //result = true;
                        result = snapshot.getValue().toString();
                        done.countDown();
                    }
                    else{
                        //result = false;
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
            return "Error";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "Error";
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
                        //result = true;
                        result = snapshot.getValue().toString();
                        done.countDown();
                    }
                    else{
                        //result = false;
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
            return "Error";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    @Override
    public List<Account> getAccount() {
        return null;
    }
}
