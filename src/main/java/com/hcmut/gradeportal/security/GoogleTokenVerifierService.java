package com.hcmut.gradeportal.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.util.Collections;

@Service
public class GoogleTokenVerifierService {
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String CLIENT_ID;

    private final GoogleIdTokenVerifier verifier;

    public GoogleTokenVerifierService() {
        JsonFactory jsonFactory = new GsonFactory(); // Sử dụng GsonFactory thay vì JacksonFactory
        verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();
    }

    public GoogleIdToken.Payload verify(String idTokenString) throws Exception {
        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            return idToken.getPayload();
        } else {
            throw new IllegalArgumentException("Invalid ID token.");
        }
    }
}
