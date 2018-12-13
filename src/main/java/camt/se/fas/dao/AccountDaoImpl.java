package camt.se.fas.dao;

import camt.se.fas.entity.Account;
import camt.se.fas.service.AESService;
import camt.se.fas.service.AESServiceImpl;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Repository
public class AccountDaoImpl implements AccountDao {
    Logger LOGGER = LoggerFactory.getLogger(AccountDaoImpl.class);

    @Override
    public String createAccount(Account account) throws FirebaseAuthException {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(account.getEmail())
                .setEmailVerified(false)
                .setPassword(account.getPassword())
                .setDisabled(false);
        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
        LOGGER.info("Get UID: " + userRecord.getUid());
        return userRecord.getUid();
    }

    @Override
    public Boolean addAccountInfo(Account account) throws ExecutionException, InterruptedException, FirebaseAuthException {
        LOGGER.info("TELEPHONE " + account.getPhonenumber().replaceAll("\\s", ""));
        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(account.getUid())
                .setPhoneNumber(account.getPhonenumber().replaceAll("\\s", ""));
        UserRecord userRecord = FirebaseAuth.getInstance().updateUser(request);
        LOGGER.info("update user " + userRecord.getUid());
        Firestore db = FirestoreClient.getFirestore();
        Map<String, Object> accountTableMap = new HashMap<>();
        if (account.getStudentId() != null) {
            accountTableMap.put("studentId", account.getStudentId());
        }
        if (account.getPhonenumber() != null) {
            accountTableMap.put("phonenumber", account.getPhonenumber().replaceAll("\\s", ""));
        }
        if (account.getDateofbirth() != null) {
            accountTableMap.put("dateofbirth", account.getDateofbirth());
        }
        if (account.getFirstname() != null) {
            accountTableMap.put("firstname", account.getFirstname());
        }
        if (account.getLastname() != null) {
            accountTableMap.put("lastname", account.getLastname());
        }
        if (account.getRandomText() != null) {
            accountTableMap.put("randomtext", account.getRandomText());
        }
        if (account.getImages().size() != 0) {
            ArrayList<Object> arrayImages = new ArrayList<>();
            Collections.addAll(arrayImages, account.getImages().get(0));
            accountTableMap.put("images", arrayImages);
        }

        ApiFuture<QuerySnapshot> future = db.collection("account").whereEqualTo("uid", account.getUid()).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (DocumentSnapshot document : documents) {
            LOGGER.info(document.getId());
            ApiFuture<WriteResult> referenceApiFuture = db.collection("account").document(document.getId()).update(accountTableMap);
            LOGGER.info(referenceApiFuture.get().getUpdateTime().toString());
            if (referenceApiFuture.isDone()) {
                return true;
            } else {
                return false;
            }
        }
        return false;


    }

    @Override
    public Boolean changeAccountStatus(Account account) throws ExecutionException, InterruptedException, FirebaseAuthException {
        if (account.getStatus().equalsIgnoreCase("registered")) {
            Firestore db = FirestoreClient.getFirestore();
            Map<String, Object> accountTableMap = new HashMap<>();
            accountTableMap.put("status", account.getStatus());
            accountTableMap.put("uid", account.getUid());
            ApiFuture<DocumentReference> documentReferenceApiFuture = db.collection("account").add(accountTableMap);
            LOGGER.info("UpdateTime " + documentReferenceApiFuture.get().getId());
            LOGGER.info("Status Result " + documentReferenceApiFuture.isDone());
            if (documentReferenceApiFuture.isDone()) {
                return true;
            } else {
                return false;
            }
        } else {
            if (account.getStatus().equalsIgnoreCase("activated")) {
                UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(account.getUid())
                        .setEmailVerified(true);
                UserRecord userRecord = FirebaseAuth.getInstance().updateUser(request);
                LOGGER.info("update user " + userRecord.getUid() + " " + userRecord.isEmailVerified());
            }
            Firestore db = FirestoreClient.getFirestore();
            Map<String, Object> accountTableMap = new HashMap<>();
            accountTableMap.put("status", account.getStatus());

            ApiFuture<QuerySnapshot> future = db.collection("account").whereEqualTo("uid", account.getUid()).get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (DocumentSnapshot document : documents) {
                ApiFuture<WriteResult> writeResultApiFuture = db.collection("account").document(document.getId()).update(accountTableMap);
                LOGGER.info("UpdateTime " + writeResultApiFuture.get().getUpdateTime());
                if (writeResultApiFuture.isDone()) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }
    }


    /*@Override
    public Boolean addStatus(Account account) throws ExecutionException, InterruptedException {

        *//*Firestore db= FirestoreClient.getFirestore();
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
        }*//*
        return null;

    }*/

   /* @Override
    public Boolean updateStatus(Account account, String status) throws ExecutionException, InterruptedException, FirebaseAuthException {
      *//*  if(status.equalsIgnoreCase("activated")){
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
            //LOGGER.info("Status Result " + writeResultApiFuture.isDone());
            if(writeResultApiFuture.isDone()) {
                return true;
            }else{
                return false;
            }
        }return false;*//*
      return null;

    }*/


    @Override
    public Account getAccountByUID(String uid) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        Account account = new Account();
        ApiFuture<QuerySnapshot> future = db.collection("account").whereEqualTo("uid", uid).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (DocumentSnapshot document : documents) {
            account.setUid((String) document.get("uid"));
            account.setFirstname((String) document.get("firstname"));
            account.setLastname((String) document.get("lastname"));
            account.setDateofbirth((String) document.get("dateofbirth"));
            account.setStudentId((String) document.get("studentId"));
            account.setRandomText((String) document.get("randomtext"));
            account.setStatus((String) document.get("status"));
            account.setPhonenumber((String) document.get("phonenumber"));
            List<String> groupImage = (List<String>) document.get("images");
            account.setImages(groupImage);
//            ApiFuture<QuerySnapshot> futureImage = document.getReference().collection("images").get();
//            List<QueryDocumentSnapshot> documentImages = futureImage.get().getDocuments();
//            for (DocumentSnapshot documentSnapshot : documentImages){
//                List<String> listImage = new ArrayList<>();
//                String image = (String) documentSnapshot.get("imageUrl");
//                listImage.add(image);
//                account.setImages(listImage);
//            }


            LOGGER.info("GET Account " + account);
        }
        return account;
    }

    @Override
    public String getEmailByUID(String uid) throws FirebaseAuthException {
        UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
        LOGGER.info("findEmailByUID " + userRecord.getEmail());
        return userRecord.getEmail();
    }

    @Override
    public String getPhonenumberByUID(String uid) throws FirebaseAuthException {
        UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
        LOGGER.info("getPhonenumberByUID " + userRecord.getPhoneNumber());
        return userRecord.getPhoneNumber();
    }

    @Override
    public Boolean findAccountByStudentId(String studentId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection("account").whereEqualTo("studentId", studentId).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (DocumentSnapshot document : documents) {
            if (document.getId() != null) {
                return true;
            } else return false;
        }
        return false;
    }

    @Override
    public Boolean findAccountByphonenumber(String phonenumber) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection("account").whereEqualTo("phonenumber", phonenumber).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (DocumentSnapshot document : documents) {
            if (document.getId() != null) {
                return true;
            } else return false;
        }
        return false;
    }

    @Override
    public List<Account> getAccountByType(String type) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection("account").whereEqualTo("type", type).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Account> accounts = new ArrayList<>();
        for (DocumentSnapshot document : documents) {
            Account account = new Account();
            account.setUid((String) document.get("uid"));
            account.setFirstname((String) document.get("firstname"));
            account.setLastname((String) document.get("lastname"));
            accounts.add(account);
            LOGGER.info("GET Account " + account);
        }
        return accounts;
    }

    @Override
    public List<Account> getAccountByStatus(String status) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection("account").whereEqualTo("status", status).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if(!documents.isEmpty()) {
            List<Account> accounts = new ArrayList<>();
            for (DocumentSnapshot document : documents) {
                Account account = new Account();
                account.setUid((String) document.get("uid"));
                account.setFirstname((String) document.get("firstname"));
                account.setLastname((String) document.get("lastname"));
                account.setDateofbirth((String) document.get("dateofbirth"));
                account.setStudentId((String) document.get("studentId"));
                account.setRandomText((String) document.get("randomtext"));
                account.setStatus((String) document.get("status"));
                account.setPhonenumber((String) document.get("phonenumber"));
                List<String> groupImage = (List<String>) document.get("images");
                account.setImages(groupImage);
                accounts.add(account);
                LOGGER.info("GET Account " + account);
            }
            return accounts;
        }else{
            return null;
        }
    }

    @Override
    public Boolean saveReasonByUID(String reason, String uid) throws ExecutionException, InterruptedException {

        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> future = db.collection("reason").whereEqualTo("uid", uid).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        LOGGER.debug("REASON |Check reason document "+documents.isEmpty());
        Map<String, Object> accountTableMap = new HashMap<>();
        accountTableMap.put("reason", reason);
        if (documents.isEmpty()){
            accountTableMap.put("uid", uid);
            ApiFuture<DocumentReference> documentReferenceApiFuture = db.collection("reason").add(accountTableMap);
            LOGGER.info("REASON |UpdateTime Resaon " + documentReferenceApiFuture.get().getId());
            LOGGER.info("REASON |Status Result Reason" + documentReferenceApiFuture.isDone());
            if (documentReferenceApiFuture.isDone()) {
                return true;
            } else {
                return false;
            }
        }else{
            for (DocumentSnapshot document : documents) {
                ApiFuture<WriteResult> writeResultApiFuture = db.collection("reason").document(document.getId()).update(accountTableMap);
                LOGGER.info("REASON |UpdateTime " + writeResultApiFuture.get().getUpdateTime());
                if (writeResultApiFuture.isDone()) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public String getReasonByUID(String uid) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection("reason").whereEqualTo("uid", uid).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (DocumentSnapshot document : documents) {
            return (String) document.get("reason");
        }
        return null;
    }

    @Override
    public Boolean saveImage(Account account) throws ExecutionException, InterruptedException {
        String s = "";
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection("account").whereEqualTo("uid", account.getUid()).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (DocumentSnapshot document : documents) {

            LOGGER.info(document.getId());
            ApiFuture<WriteResult> referenceApiFuture = db.collection("account").document(document.getId()).update("images",
                    FieldValue.arrayUnion(account.getImages().get(0)));

            LOGGER.info(String.valueOf(referenceApiFuture.get()));
            return true;
        }

        return null;
    }

}
