package camt.se.fas.service;

import com.google.firebase.auth.FirebaseToken;

public interface FirebaseAuthenticationService {
    FirebaseToken parseToken(String idToken);
}
