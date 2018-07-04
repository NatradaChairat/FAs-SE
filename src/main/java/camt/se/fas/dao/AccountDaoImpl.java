package camt.se.fas.dao;

import camt.se.fas.entity.Account;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Profile("DBDataSource")
@Repository
public class AccountDaoImpl implements AccountDao {

    @Value("${firebase.database-url}")
    String firebaseUrl;

    @Value("${firebase.config.path}")
    String firebaseConfigPath;

    DatabaseReference databaseReference;

    /*AccountDaoImpl() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(firebaseConfigPath);
        //InputStream serviceAccount = AccountDaoImpl.class.getClassLoader().getResourceAsStream(firebaseConfigPath);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(firebaseUrl)
                .build();

        FirebaseApp.initializeApp(options);

    }*/

    @Override
    public Account addAccount(Account account) {
        try {
            //FileInputStream serviceAccount = new FileInputStream("D://FAs/FacialAuthentication-SE/src/main/resource/"+firebaseConfigPath);
            InputStream serviceAccount = AccountDaoImpl.class.getClassLoader().getResourceAsStream(firebaseConfigPath);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(firebaseUrl)
                    .build();

            FirebaseApp.initializeApp(options);

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
