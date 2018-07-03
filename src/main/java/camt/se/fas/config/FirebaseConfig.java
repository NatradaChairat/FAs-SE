package camt.se.fas.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.apache.http.auth.AuthScope;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.path}")
    private String path;

    @Value("${firebase.database-url}")
    private String databaseUrl;

    @Value(value = "classpath:google-services.json")
    private Resource gservicesConfig;
/*
    @Bean
    public FirebaseApp provideFirebaseOptions() throws IOException {
        //InputStream inputStream = FirebaseConfig.class.getClassLoader().getResourceAsStream(path);
        FirebaseOptions options = new FirebaseOptions.Builder().setDatabaseUrl(databaseUrl).build();
        return FirebaseApp.initializeApp(options);
    }

    @Bean
    *//*@Qualifier("main")*//*
    public DatabaseReference provideDatabaseReference(FirebaseApp firebaseApp){
        FirebaseDatabase.getInstance(firebaseApp).setPersistenceEnabled(false);
        return FirebaseDatabase.getInstance(firebaseApp).getReference();
    }*/
}
