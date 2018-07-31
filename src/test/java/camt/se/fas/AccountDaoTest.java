package camt.se.fas;

import camt.se.fas.dao.AccountDao;
import com.google.firebase.database.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PowerMockIgnore("javax.management.*")
@SpringBootTest
public class AccountDaoTest {
    private DatabaseReference mockDatabaseRef;
    @Autowired
    AccountDao accountDao;

    @Before
    public void setUp() {
        mockDatabaseRef = Mockito.mock(DatabaseReference.class);

        FirebaseDatabase mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        when(mockedFirebaseDatabase.getReference()).thenReturn(mockDatabaseRef);

        PowerMockito.mockStatic(FirebaseDatabase.class);
        when(FirebaseDatabase.getInstance()).thenReturn(mockedFirebaseDatabase);

    }

    @Test
    public void testFindAccountByEmail(){
        when(mockDatabaseRef.child(anyString())).thenReturn(mockDatabaseRef);
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ValueEventListener valueEventListener  = (ValueEventListener) invocation.getArguments()[0];
                DataSnapshot mockDataSnapshot = Mockito.mock(DataSnapshot.class);
                valueEventListener.onDataChange(mockDataSnapshot);
                return null;
            }
        }).when(mockDatabaseRef).addListenerForSingleValueEvent(any(ValueEventListener.class));
        Assert.assertEquals(null, accountDao.findAccountByEmail("Cyymerr"));
    }

    @Test
    public void testFindAccountByEmail2() {
        mockDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}
