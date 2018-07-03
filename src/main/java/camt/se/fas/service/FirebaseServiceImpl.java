package camt.se.fas.service;

import camt.se.fas.entity.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FirebaseServiceImpl implements FirebaseService {
 /*   @Autowired
    @Qualifier("main")
    DatabaseReference mainDatabaseReference;*/

    @Value("${firebase.path}")
    private String path;

    /*@Override
    public void startFirebaseListener() {
        mainDatabaseReference.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Account account = snapshot.getValue(Account.class);
                System.out.println(account);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("Firebase Listener fail");
            }
        });
    }*/
}
