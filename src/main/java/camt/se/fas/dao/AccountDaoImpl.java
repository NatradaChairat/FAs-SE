package camt.se.fas.dao;

import camt.se.fas.entity.Account;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserImportHash;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
public class AccountDaoImpl implements AccountDao{
    Logger LOGGER = LoggerFactory.getLogger(AccountDaoImpl.class);

    @Override
    public String createAccount(Account account) throws FirebaseAuthException {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(account.getEmail())
                .setEmailVerified(false)
                .setPassword(account.getPassword())
                .setDisabled(false);
            UserRecord userRecord=  FirebaseAuth.getInstance().createUser(request);
            LOGGER.info("Get UID: "+userRecord.getUid());
            return  userRecord.getUid();
    }

    @Override
    public Boolean addAccountInfo(Account account) throws ExecutionException, InterruptedException, FirebaseAuthException {
        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(account.getUid())
                .setPhoneNumber(account.getPhonenumber().replaceAll("\\s+",""));
        UserRecord userRecord = FirebaseAuth.getInstance().updateUser(request);
        LOGGER.info("update user "+userRecord.getUid());
        LOGGER.info(account.toString());
        Firestore db= FirestoreClient.getFirestore();
        Map<String, Object> accountTableMap = new HashMap<>();
        if( account.getStudentId() != null) {
            accountTableMap.put("studentId", account.getStudentId());
        }
        if( account.getPhonenumber() != null) {
            accountTableMap.put("phonenumber", account.getPhonenumber());
        }
        if( account.getDateofbirth() != null) {
            accountTableMap.put("dateofbirth", account.getDateofbirth());
        }
        if (account.getFirstname() !=null) {
            accountTableMap.put("firstname", account.getFirstname());
        }
        if(account.getLastname() !=null) {
            accountTableMap.put("lastname", account.getLastname());
        }
        ApiFuture<QuerySnapshot> future = db.collection("account").whereEqualTo("uid", account.getUid()).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (DocumentSnapshot document : documents) {
            ApiFuture<WriteResult> referenceApiFuture = db.collection("account").document(document.getId()).update(accountTableMap);
            if (referenceApiFuture.isDone()) {
                return true;
            } else {
                return false;
            }
        }return false;
    }


    @Override
    public Boolean addStatus(Account account) throws ExecutionException, InterruptedException {

        Firestore db= FirestoreClient.getFirestore();
        Map<String, Object> accountTableMap = new HashMap<>();
        accountTableMap.put("status", "registered");
        accountTableMap.put("uid", account.getUid());
        ApiFuture<DocumentReference> documentReferenceApiFuture = db.collection("account").add(accountTableMap);
        LOGGER.info("UpdateTime "+documentReferenceApiFuture.get().getId());
        LOGGER.info("Status Result "+documentReferenceApiFuture.isDone());
        if(documentReferenceApiFuture.isDone()){
            return true;
        }else{
            return false;
        }

    }

    @Override
    public Boolean updateStatus(Account account, String status) throws ExecutionException, InterruptedException, FirebaseAuthException {
        if(status.equalsIgnoreCase("activated")){
            UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(account.getUid())
                    .setEmailVerified(true);
            UserRecord userRecord = FirebaseAuth.getInstance().updateUser(request);
            LOGGER.info("update user "+userRecord.getUid());
        }
        Firestore db= FirestoreClient.getFirestore();
        Map<String, Object> accountTableMap = new HashMap<>();
        accountTableMap.put("status", status);

        ApiFuture<QuerySnapshot> future = db.collection("account").whereEqualTo("uid", account.getUid()).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (DocumentSnapshot document : documents) {
            ApiFuture<WriteResult> writeResultApiFuture = db.collection("account").document(document.getId()).update(accountTableMap);
            LOGGER.info("UpdateTime " + writeResultApiFuture.get().getUpdateTime());
            LOGGER.info("Status Result " + writeResultApiFuture.isDone());
            if(writeResultApiFuture.isDone()) {
                return true;
            }else{
                return false;
            }
        }return false;

    }


    @Override
    public Boolean findAccountByAccountId(String accountId) {
        return null;
    }

    @Override
    public String findEmailByUID(String uid) throws FirebaseAuthException {
        UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
        LOGGER.info("findEmailByUID "+userRecord.getEmail());
        return userRecord.getEmail();
    }

    @Override
    public String findIdByStudentId(String studentId) throws ExecutionException, InterruptedException {
        Firestore db= FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection("account").whereEqualTo("studentId", studentId).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (DocumentSnapshot document : documents) {
            return document.getId();
        }return null;
    }

    @Override
    public String findIdByphonenumber(String phonenumber) throws ExecutionException, InterruptedException {
        Firestore db= FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection("account").whereEqualTo("phonenumber", phonenumber).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (DocumentSnapshot document : documents) {
            return document.getId();
        }return null;
    }
}
