package camt.se.fas.service;

import camt.se.fas.dao.AccountDao;
import camt.se.fas.entity.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class.getName());

    /*@Value("${firebase.database-url}")
    String firebaseUrl;

    @Value("${firebase.config.path}")
    String firebaseConfigPath;

    DatabaseReference databaseReference;*/

    @Autowired
    AccountDao accountDao;

    @Override
    public Account testDao() {
        return accountDao.findAccountByAccountId("FA00088");
    }
    @Override
    public Account addAccountOfRegistrationStep1(Account account) {
        LOGGER.info("Get Last AccountId: "+ accountDao.findLastAccountId());
        String keyOnlyNumber = accountDao.findLastAccountId().getAccountId().substring(2);
        LOGGER.info("Get KEY: " + keyOnlyNumber);
        int keyNumber = Integer.parseInt(keyOnlyNumber);
        LOGGER.info("Get KEY number: " + keyNumber);
        String nextKey = "FA" + String.format("%05d", keyNumber + 1);
        LOGGER.info("Get Next KEY: " + nextKey);

        if(nextKey != "FA100000") {
            account.setAccountId(nextKey);
            Account account1 = accountDao.addUsernamePasswordStudentId(account);
            LOGGER.info("Added1: "+account1 );
            Account account2 = accountDao.addStatus(account, "registered");
            LOGGER.info("Added2: "+ account2);
            Account account3 = accountDao.addEmailPhonenumber(account);
            LOGGER.info("Added3: "+ account3);
            account = account3;
            return account;
        }else return null;

    }

    @Override
    public Account updateAccountOfRegistrationStep2(Account account) {
        LOGGER.info("Get KEY: " + account.getAccountId());
        Account account1 = accountDao.addEmailPhonenumber(account);
        LOGGER.info("Get account1: " + account1);
        Account account2 = accountDao.addUsernamePasswordStudentId(account);
        LOGGER.info("Get account2: " + account2);
        Account account3 = accountDao.addDOBFirstnameLastname(account);
        LOGGER.info("Get account3: " + account3);
        return account3;
    }

    @Override
    public Account updateStatus(Account account, String status) {
        return accountDao.addStatus(account,status);
    }

    /*@Override
    public Boolean addAccountOfRegisterStepOne(Account account) {
        System.out.println(account.toString());
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

                    String a = addUsernamePasswordToDB(newKey, account);
                    String b = addStatusToDB(newKey,"registered");
                    String c = addEmailPhonenumberToDB(newKey, account);
                    try {
                        Thread.sleep(3000);
                        System.out.println("1: "+a+" 2: "+b+" 3: "+c);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onCancelled (DatabaseError error){

                }
            });
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String addUsernamePasswordToDB(String accountId, Account account) {
        System.out.println("addUsernamePasswordToDB working ");
        DatabaseReference usersRef = databaseReference.child("account_table");
        Map<String, Object> accountTableMap = new HashMap<>();
        accountTableMap.put("username", account.getUsername());
        accountTableMap.put("password", account.getPassword());
        accountTableMap.put("studentId", account.getStudentId());
        usersRef.child(accountId).setValueAsync(accountTableMap);
        return usersRef.child(accountId).getKey().toString();
    }

    @Override
    public String addStatusToDB(String accountId, String status) {
        System.out.println("addStatusToDB working ");
        DatabaseReference usersRef = databaseReference.child("account_status_table");
        Map<String, Object> accountTableMap = new HashMap<>();
        accountTableMap.put(accountId, status);
        usersRef.updateChildrenAsync(accountTableMap);
        return usersRef.child(accountId).getKey();
    }

    @Override
    public String addEmailPhonenumberToDB(String accountId, Account account) {
        System.out.println("addEmailPhonenumberToDB working ");
        DatabaseReference usersRef = databaseReference.child("account_contact_table");
        Map<String, Object> accountTableMap = new HashMap<>();
        accountTableMap.put("email", account.getEmail());
        accountTableMap.put("phonenumber", account.getPhonenumber());
        usersRef.child(accountId).updateChildrenAsync(accountTableMap);
        return usersRef.child(accountId).getKey().toString();
    }*/


    //private String result;
    @Override
    public Account findAccountByUsername(String username) {
       // Account _account = accountDao.findAccountByUsername(username);
        return accountDao.findAccountByUsername(username);
        /*try {
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
                    System.out.println("FindByUsername Get: "+snapshot.getValue());
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
        }*/
    }

    @Override
    public Account findAccountByEmail(String email) {
        Account _account = accountDao.findAccountByEmail(email);
        return _account;
        /*try {
            CountDownLatch done = new CountDownLatch(1);
            InputStream serviceAccount = AccountServiceImpl.class.getClassLoader().getResourceAsStream(firebaseConfigPath);
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    //.setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(firebaseUrl)
                    .build();
            if(FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

            databaseReference = FirebaseDatabase.getInstance().getReference();
            System.out.println("databaseReference: ");
            databaseReference.child("account_contact_table").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    System.out.println("FindbyEmail Get: "+snapshot.getValue());
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
        }*/
    }

    @Override
    public Account findAccountByStudentId(String studentId) {
        return accountDao.findAccountByStudentId(studentId);
    }

    @Override
    public Account findAccountByPhonenumber(String phonenumber) {
        return accountDao.findAccountByPhonenumber(phonenumber);
    }

   /* @Override
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
                    //addStatusToDB(accountId,status);
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
    }*/
    @Override
    public List<Account> getAccounts() {
        return null;
    }

    @Override
    public Account getAccount(String accountId) {
        Account account = accountDao.findAccountByAccountId(accountId);
        return account;
    }

}
