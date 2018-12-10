package camt.se.fas.service.old;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

public interface FirebaseAuthenticationService {
    FirebaseToken verifyIDToken(String idToken) throws FirebaseAuthException;
    String createCustomToken(String uid) throws FirebaseAuthException;
}
