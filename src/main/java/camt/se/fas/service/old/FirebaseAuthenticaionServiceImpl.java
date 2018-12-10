package camt.se.fas.service.old;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

public class FirebaseAuthenticaionServiceImpl implements FirebaseAuthenticationService {

    @Override
    public FirebaseToken verifyIDToken(String idToken) throws FirebaseAuthException {
        FirebaseToken decodeToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        return null;
    }

    @Override
    public String createCustomToken(String uid) throws FirebaseAuthException {
        return FirebaseAuth.getInstance().createCustomToken(uid);
    }
}
