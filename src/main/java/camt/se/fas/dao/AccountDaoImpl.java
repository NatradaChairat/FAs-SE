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

import java.util.Date;
import java.util.HashMap;
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
    public Account addUsernamePassword(Account account) {
        Account _account = new Account();
        LOGGER.info("Add Username, Password Student Id: "+account.getUsername()+", "+account.getPassword());
        DatabaseReference usersRef = databaseReference.child("account_table");
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
            //return usersRef.child(account.getAccountId()).getKey().toString();
            //usersRef.child(account.getAccountId()).updateChildrenAsync(accountTableMap);

        CountDownLatch signal = new CountDownLatch(1);
        try {
            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    _account.setAccountId(account.getAccountId());
                    _account.setUsername((String) snapshot.child(account.getAccountId()).child("username").getValue());
                    _account.setPassword((String) snapshot.child(account.getAccountId()).child("password").getValue());
                    _account.setStudentId((String) snapshot.child(account.getAccountId()).child("studentId").getValue());
                    LOGGER.info("Get Account: "+_account);
                    signal.countDown();
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
            signal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            return _account;
        }

    }

    @Override
    public Account addStatus(Account account,String status) {
        LOGGER.info("Add Status: "+account.getAccountId()+", "+status);
        DatabaseReference usersRef = databaseReference.child("account_status_table");
        Map<String, Object> accountTableMap = new HashMap<>();
        accountTableMap.put(account.getAccountId(), status);
        usersRef.updateChildrenAsync(accountTableMap);
        Account _account = new Account();
        CountDownLatch signal = new CountDownLatch(1);
        try {
            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    _account.setAccountId(account.getAccountId());
                    _account.setStatus((String) snapshot.child(account.getAccountId()).getValue());
                    LOGGER.info("Get Account: "+_account);
                    signal.countDown();
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
            signal.await();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            return _account;
        }
    }

    @Override
    public Account addEmailPhonenumber(Account account) {
        LOGGER.info("Add Email, Phonenumber: "+account.getEmail()+ ", "+account.getPhonenumber());
        DatabaseReference usersRef = databaseReference.child("account_contact_table");
        Map<String, Object> accountTableMap = new HashMap<>();
        if( account.getEmail() != null) {
            accountTableMap.put("email", account.getEmail().toLowerCase());
        }
        if( account.getPhonenumber() != null) {
            accountTableMap.put("phonenumber", account.getPhonenumber());
        }
        Account _account = new Account();
        usersRef.child(account.getAccountId()).updateChildrenAsync(accountTableMap);
        CountDownLatch signal = new CountDownLatch(1);
        try {
            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    _account.setAccountId(account.getAccountId());
                    _account.setEmail((String) snapshot.child(account.getAccountId()).child("email").getValue());
                    _account.setPhonenumber((String) snapshot.child(account.getAccountId()).child("phonenumber").getValue());
                    LOGGER.info("Get Account: "+_account);
                    signal.countDown();
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
            signal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            return _account;
        }
    }

    @Override
    public Account addDOBFirstnameLastname(Account account) {
        LOGGER.info("Add DOB, Firstname, Lastname: "+account.getDateofbirth()+ ", "+account.getFirstname()+", "+account.getLastname());
        DatabaseReference usersRef = databaseReference.child("account_info_table");
        Map<String, Object> accountTableMap = new HashMap<>();
        accountTableMap.put("dateofbirth", account.getDateofbirth());
        accountTableMap.put("firstname", account.getFirstname());
        accountTableMap.put("lastname", account.getLastname());
        Account _account = new Account();
        usersRef.child(account.getAccountId()).updateChildrenAsync(accountTableMap);
        CountDownLatch signal = new CountDownLatch(1);
        try {
            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    _account.setAccountId(account.getAccountId());
                    _account.setDateofbirth((String) snapshot.child(account.getAccountId()).child("dateofbirth").getValue());
                    _account.setFirstname((String) snapshot.child(account.getAccountId()).child("firstname").getValue());
                    _account.setLastname((String) snapshot.child(account.getAccountId()).child("lastname").getValue());
                    LOGGER.info("Get Account: "+_account);
                    signal.countDown();
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
            signal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            return _account;
        }
    }

    @Override
    public Account findAccountByEmail(String email) {
        Account account = new Account();
        System.out.println("findAccount work");
        try {
            System.out.println("try work | Get ref:" + databaseReference.getRef());
            databaseReference.child("account_contact_table").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                LOGGER.info("findAccountByEmail: "+snapshot.toString());
                if(snapshot.getValue() != null) {
                    System.out.println("onDataChange work");
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

        Thread.sleep(1000);
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
            Thread.sleep(1000);
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
    public Account findAccountByStudentId(String studentId) {
        Account account = new Account();
        try {
            databaseReference.child("account_table").orderByChild("studentId").equalTo(studentId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    LOGGER.info("findAccountByStudentId: "+snapshot.toString());
                    if(snapshot.getValue()!=null) {
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

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    System.out.println(error.getDetails());
                }
            });
            Thread.sleep(1000);
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
    public Account findAccountByPhonenumber(String phonenumber) {
        Account account = new Account();
        CountDownLatch signal = new CountDownLatch(1);
        try {
        databaseReference.child("account_contact_table").orderByChild("phonenumber").equalTo(phonenumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                LOGGER.info("findAccountByPhonenumber: "+snapshot.toString());
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
                signal.countDown();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        signal.await();
        Thread.sleep(1000);
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

    @Override
    public Account findAccountByAccountId(String accountId) {
        LOGGER.info("findAccountByAccountId() working");
        CountDownLatch signal = new CountDownLatch(1);
        Account account = new Account();
        account.setAccountId(accountId);
        try {
        databaseReference.child("account_table").orderByKey().equalTo(accountId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                LOGGER.info(snapshot.getValue().toString());
                String _username = snapshot.child(accountId).child("username").getValue().toString();
                LOGGER.info("Get Username "+_username);
                account.setUsername(_username);
                String _studentId = snapshot.child(accountId).child("studentId").getValue().toString();
                LOGGER.info("Get StudentId "+_studentId);
                account.setStudentId(_studentId);
                signal.countDown();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        databaseReference.child("account_contact_table").orderByKey().equalTo(accountId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                LOGGER.info(snapshot.getValue().toString());
                String _email = snapshot.child(accountId).child("email").getValue().toString();
                LOGGER.info("Get Email "+_email);
                account.setEmail(_email);
                signal.countDown();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        Thread.sleep(2000);
        signal.await();
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }finally {
            LOGGER.info(account.toString());
            if(account.getEmail()!=null && account.getUsername()!=null) {
                return account;
            }else return null;
        }
    }

    @Override
    public Account updateStatusByAccountId(String accountId, String status) {
        return null;
    }


}
