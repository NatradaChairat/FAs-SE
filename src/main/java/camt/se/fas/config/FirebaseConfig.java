package camt.se.fas.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Configuration
//@Component
public class FirebaseConfig {
    Logger LOGGER = LoggerFactory.getLogger(FirebaseConfig.class.getName());
    @Bean
    public DatabaseReference firebaseDatabase(){
        LOGGER.info("firebaseDatabase() working");
        DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
        return firebase;
    }

    @Value("${firebase.database-url}")
    String firebaseDatabaseUrl;

    @Value("${firebase.config.path}")
    String firebaseConfigPath;

    @Value("${firebase.storage-url}")
    String firebaseStorageUrl;

    @PostConstruct
    public void init() throws IOException {
        LOGGER.info("init() working");
        InputStream serviceAccount = FirebaseConfig.class.getClassLoader().getResourceAsStream(firebaseConfigPath);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(firebaseDatabaseUrl)
                .setStorageBucket(firebaseStorageUrl)
                .build();
        if(FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }


    }

    
}
