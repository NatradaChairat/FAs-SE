package camt.se.fas.dao;

import camt.se.fas.config.FirebaseConfig;
import camt.se.fas.entity.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Repository
public class AccountDaoImpl implements AccountDao {

    Logger LOGGER = LoggerFactory.getLogger(AccountDaoImpl.class.getName());
    DatabaseReference databaseReference;

    @Autowired
    public void setDatabaseReference (FirebaseConfig firebaseConfig){
        this.databaseReference = firebaseConfig.firebaseDatabase();
    }

    @Override
    public Account addUsernamePasswordStudentId(Account account) {
        Account _account = new Account();
        LOGGER.info(account.getAccountId()+"Add Username, Password Student Id: "+account.getUsername()+", "+account.getPassword());
        DatabaseReference usersRef = databaseReference.child("account");
        Map<String, Object> accountTableMap = new HashMap<>();
        if( account.getUsername() != null) {
            accountTableMap.put("username", account.getUsername());
        }
        if( account.getPassword() != null) {
            accountTableMap.put("password", account.getPassword());
        }
        if( account.getStudentId() != null) {
            accountTableMap.put("studentId", account.getStudentId());
        }
        usersRef.child(account.getAccountId()).updateChildrenAsync(accountTableMap);
        _account = findAccountByAccountId(account.getAccountId());
        LOGGER.info("Result addUsernamePasswordStudentId(): "+_account);
        return _account;
    }

    @Override
    public Account addStatus(Account account, String status) {
        LOGGER.info("Add Status: "+account.getAccountId()+", "+status);
        DatabaseReference usersRef = databaseReference.child("account");
        Map<String, Object> accountTableMap = new HashMap<>();
        accountTableMap.put("status", status);
        usersRef.child(account.getAccountId()).updateChildrenAsync(accountTableMap);
        Account _account = new Account();
        CountDownLatch signal = new CountDownLatch(1);
        _account = findAccountByAccountId(account.getAccountId());
        LOGGER.info("Result addStatus(): "+_account);
        return _account;
    }

    @Override
    public Account addEmailPhonenumber(Account account) {
        LOGGER.info("Add Email, Phonenumber: "+account.getEmail()+ ", "+account.getPhonenumber());
        DatabaseReference usersRef = databaseReference.child("account");
        Map<String, Object> accountTableMap = new HashMap<>();
        if( account.getEmail() != null) {
            accountTableMap.put("email", account.getEmail().toLowerCase());
        }
        if( account.getPhonenumber() != null) {
            accountTableMap.put("phonenumber", account.getPhonenumber());
        }
        Account _account = new Account();
        usersRef.child(account.getAccountId()).updateChildrenAsync(accountTableMap);
        _account = findAccountByAccountId(account.getAccountId());
        LOGGER.info("Result addEmailPhonenumber(): "+_account);
        return _account;
    }

    @Override
    public Account addDOBFirstnameLastname(Account account) {
        LOGGER.info("Add DOB, Firstname, Lastname: "+account.getDateofbirth()+ ", "+account.getFirstname()+", "+account.getLastname());
        DatabaseReference usersRef = databaseReference.child("account");
        Map<String, Object> accountTableMap = new HashMap<>();
        accountTableMap.put("dateofbirth", account.getDateofbirth());
        accountTableMap.put("firstname", account.getFirstname());
        accountTableMap.put("lastname", account.getLastname());
        Account _account = new Account();
        usersRef.child(account.getAccountId()).updateChildrenAsync(accountTableMap);
        CountDownLatch signal = new CountDownLatch(1);
        _account = findAccountByAccountId(account.getAccountId());
        LOGGER.info("Result addDOBFirstnameLastname(): "+_account);
        return _account;
    }

    @Override
    public Account findAccountByEmail(String email) {
        Account account = new Account();
        CountDownLatch signal = new CountDownLatch(1);
        try {
            databaseReference.child("account").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if(snapshot.getValue() != null) {
                        String result = snapshot.getValue().toString().substring(1, 8);
                        LOGGER.info(result);
                        account.setAccountId(result);
                        /*String _email = snapshot.child(result).child("email").getValue().toString();
                        LOGGER.info(_email);
                        account.setEmail(_email);*/
                        /*String _phonenumber = snapshot.child(result).child("phonenumber").getValue().toString();
                        LOGGER.info(_phonenumber);
                        account.setPhonenumber(_phonenumber);*/
                    }
                    signal.countDown();
                }
                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
            signal.await();
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }finally {
            LOGGER.info(account.toString());
            if(account.getAccountId()!=null) {
                return findAccountByAccountId(account.getAccountId());
            }else return null;
        }
    }

    @Override
    public Account findAccountByUsername(String username) {
        Account account = new Account();
        CountDownLatch signal = new CountDownLatch(1);
        try {
            databaseReference.child("account").orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if(snapshot.getValue() != null) {
                        String result = snapshot.getValue().toString().substring(1, 8);
                        LOGGER.info(result);
                        account.setAccountId(result);
                        /*String _username = snapshot.child(result).child("username").getValue().toString();
                        LOGGER.info(_username);
                        account.setUsername(_username);*/
                        /*String _password = snapshot.child(result).child("password").getValue().toString();
                        LOGGER.info(_password);
                        account.setPassword(_password);
                        String _studentId = snapshot.child(result).child("studentId").getValue().toString();
                        LOGGER.info(_studentId);
                        account.setStudentId(_studentId);*/
                    }
                    signal.countDown();
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
            signal.await();
            //Thread.sleep(500);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }finally {
            LOGGER.info(account.toString());
            if(account.getAccountId()!=null) {
                return findAccountByAccountId(account.getAccountId());
            }else return null;
        }
    }

    @Override
    public Account findAccountByStudentId(String studentId) {
        Account account = new Account();
        CountDownLatch signal = new CountDownLatch(1);
        try {
            databaseReference.child("account").orderByChild("studentId").equalTo(studentId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if(snapshot.getValue()!=null) {
                        String result = snapshot.getValue().toString().substring(1, 8);
                        LOGGER.info(result);
                        account.setAccountId(result);
                        /*String _username = snapshot.child(result).child("username").getValue().toString();
                        LOGGER.info(_username);
                        account.setUsername(_username);
                        String _password = snapshot.child(result).child("password").getValue().toString();
                        LOGGER.info(_password);
                        account.setPassword(_password);*/
                        String _studentId = snapshot.child(result).child("studentId").getValue().toString();
                        LOGGER.info(_studentId);
                        account.setStudentId(_studentId);
                    }
                    signal.countDown();
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    System.out.println(error.getDetails());
                }
            });
            signal.await();
            /*Thread.sleep(1000);*/
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }finally {
            LOGGER.info(account.toString());
            if(account.getAccountId()!=null) {
                return findAccountByAccountId(account.getAccountId());
            }else return null;
        }
    }

    @Override
    public Account findAccountByPhonenumber(String phonenumber) {
        Account account = new Account();
        CountDownLatch signal = new CountDownLatch(1);
        try {
            databaseReference.child("account").orderByChild("phonenumber").equalTo(phonenumber).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if(snapshot.getValue() != null) {
                        String result = snapshot.getValue().toString().substring(1, 8);
                        LOGGER.info(result);
                        account.setAccountId(result);
                        /*String _email = snapshot.child(result).child("email").getValue().toString();
                        LOGGER.info(_email);
                        account.setEmail(_email);*/
                        /*String _phonenumber = snapshot.child(result).child("phonenumber").getValue().toString();
                        LOGGER.info(_phonenumber);
                        account.setPhonenumber(_phonenumber);*/
                    }
                    signal.countDown();
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });

            signal.await();
           /* Thread.sleep(1000);*/
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }finally {
            LOGGER.info(account.toString());
            if(account.getAccountId()!=null) {
                return findAccountByAccountId(account.getAccountId());
            }else return null;
        }
    }

    @Override
    public Account findLastAccountId() {
        Account account = new Account();
        CountDownLatch signal = new CountDownLatch(1);
        try {
            databaseReference.child("account").orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
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
                    signal.countDown();
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
            signal.await();
           /* Thread.sleep(1000);*/
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }finally {
            LOGGER.info(account.toString());
            return account;
        }
    }

    @Override
    public Account findAccountByAccountId(String accountId) {
        CountDownLatch signal = new CountDownLatch(1);
        Account account = new Account();
        try {
            databaseReference.child("account").orderByKey().equalTo(accountId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    LOGGER.info("Snapshot working");
                    if(snapshot.getValue() != null) {
                        LOGGER.info(snapshot.getValue().toString());
                        String result = snapshot.getValue().toString().substring(1, 8);
                        LOGGER.info("Get result "+result);
                        account.setAccountId(result);

                        LOGGER.info("Get result "+snapshot.child(accountId).hasChild("dateofbirth"));
                        if(snapshot.child(accountId).hasChild("dateofbirth")){
                            String _dob = snapshot.child(accountId).child("dateofbirth").getValue().toString();
                            LOGGER.info("Get DOB " + _dob);
                            account.setDateofbirth(_dob);
                        }
                        LOGGER.info("Get result "+snapshot.child(accountId).hasChild("email"));
                        if(snapshot.child(accountId).hasChild("email")) {
                            String _email = snapshot.child(accountId).child("email").getValue().toString();
                            LOGGER.info("Get Email " + _email);
                            account.setEmail(_email);
                        }
                        LOGGER.info("Get result "+snapshot.child(accountId).hasChild("firstname"));
                        if(snapshot.child(accountId).hasChild("firstname")) {
                            String _firstname = snapshot.child(accountId).child("firstname").getValue().toString();
                            LOGGER.info("Get Firstname " + _firstname);
                            account.setFirstname(_firstname);
                        }
                        LOGGER.info("Get result "+snapshot.child(accountId).hasChild("lastname"));
                        if(snapshot.child(accountId).hasChild("lastname")) {
                            String _lastname = snapshot.child(accountId).child("lastname").getValue().toString();
                            LOGGER.info("Get Lastname " + _lastname);
                            account.setLastname(_lastname);
                        }
                        LOGGER.info("Get result "+snapshot.child(accountId).hasChild("password"));
                        if(snapshot.child(accountId).hasChild("password")) {
                            String _password = snapshot.child(accountId).child("password").getValue().toString();
                            LOGGER.info("Get Password " + _password);
                            account.setPassword(_password);
                        }
                        LOGGER.info("Get result "+snapshot.child(accountId).hasChild("phonenumber"));
                        if(snapshot.child(accountId).hasChild("phonenumber")) {
                            String _phonenumber = snapshot.child(accountId).child("phonenumber").getValue().toString();
                            LOGGER.info(_phonenumber);
                            account.setPhonenumber(_phonenumber);
                        }
                        LOGGER.info("Get result "+snapshot.child(accountId).hasChild("status"));
                        if(snapshot.child(accountId).hasChild("status")) {
                            String _status = snapshot.child(accountId).child("status").getValue().toString();
                            LOGGER.info("Get status " + _status);
                            account.setStatus(_status);
                        }
                        LOGGER.info("Get result "+snapshot.child(accountId).hasChild("studentId"));
                        if(snapshot.child(accountId).hasChild("studentId")) {
                            String _studentId = snapshot.child(accountId).child("studentId").getValue().toString();
                            LOGGER.info("Get StudentId " + _studentId);
                            account.setStudentId(_studentId);
                        }
                        LOGGER.info("Get result "+snapshot.child(accountId).hasChild("username"));
                        if(snapshot.child(accountId).hasChild("username")) {
                            String _username = snapshot.child(accountId).child("username").getValue().toString();
                            LOGGER.info("Get Username " + _username);
                            account.setUsername(_username);
                        }
                        LOGGER.info("Get result "+snapshot.child(accountId).hasChild("videos"));
                        if(snapshot.child(accountId).hasChild("videos")) {
                            LOGGER.info("Get video " + snapshot.child(accountId).child("videos").getChildrenCount());
                            List<String> videoList = new ArrayList<String>();
                            if (snapshot.child(accountId).child("videos").getChildrenCount() != 0) {
                                for (DataSnapshot data : snapshot.child(accountId).child("videos").getChildren()) {
                                    String sentance = data.getKey() + " : " + data.getValue();
                                    LOGGER.info("Get Video " + data.getKey() + " " + data.getValue());
                                    videoList.add(sentance);
                                }
                                account.setVideos(videoList);
                            }
                        }
                        LOGGER.info("Get result "+snapshot.child(accountId).hasChild("images"));
                        if(snapshot.child(accountId).hasChild("images")) {
                            List<String> imageList = new ArrayList<String>();
                            LOGGER.info("Get Main Image Count " + snapshot.child(accountId).child("images").getChildrenCount());
                            if (snapshot.child(accountId).child("images").getChildrenCount() != 0) {
                                imageList.clear();
                                List<String> childList = new ArrayList<String>();
                                for (DataSnapshot data : snapshot.child(accountId).child("images").getChildren()) {
                                    childList.clear();
                                    LOGGER.info("Get Main Image Count " + snapshot.child(accountId).child("images").child(data.getKey()).getChildrenCount());
                                    String sentance = data.getKey() + " : ";
                                    LOGGER.info("Get Image " + data.getKey() + " : ");
                                    for (DataSnapshot dataChild : snapshot.child(accountId).child("images").child(data.getKey()).getChildren()) {
                                        LOGGER.info("Get Child Image Count " + snapshot.child(accountId).child("images").child(data.getKey()).getChildrenCount());
                                        LOGGER.info(dataChild.toString());
                                        String sentanceChild = dataChild.getKey() + " : " + dataChild.getValue();
                                        LOGGER.info("Get Image " + dataChild.getKey() + " : " + dataChild.getValue());
                                        childList.add(sentanceChild);
                                    }
                                    sentance += childList;
                                    LOGGER.info("Get Image " + sentance);
                                    imageList.add(sentance);
                                }
                                account.setImages(imageList);
                            }
                        }
                    }
                    signal.countDown();
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
            signal.await();
            /*Thread.sleep(200);*/
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }finally {
            LOGGER.info(account.toString());
            if(account.getAccountId()!=null && account.getUsername()!=null) {
                return account;
            }else return null;
        }

    }
}
