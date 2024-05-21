package com.question.memo.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

@Configuration
public class FcmConfig {
	private static final String FIREBASE_KEY_PATH = "firebase/square-41ad3-firebase-adminsdk-vb1k1-7d1feb2f25.json";
	@Bean
	public FirebaseMessaging getFirebaseMessaging() throws IOException {
		ClassPathResource resource = new ClassPathResource(FIREBASE_KEY_PATH);
		InputStream refreshToken = resource.getInputStream();

		FirebaseApp firebaseApp = getFirebaseApp(refreshToken);
		return FirebaseMessaging.getInstance(firebaseApp);
	}

	private FirebaseApp getFirebaseApp(InputStream refreshToken) throws IOException {
		List<FirebaseApp> firebaseAppList = FirebaseApp.getApps();

		for (FirebaseApp app : firebaseAppList) {
			if (app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)) {
				return app;
			}
		}

		FirebaseOptions options = FirebaseOptions.builder()
			.setCredentials(GoogleCredentials.fromStream(refreshToken))
			.build();

		return FirebaseApp.initializeApp(options);
	}
}
