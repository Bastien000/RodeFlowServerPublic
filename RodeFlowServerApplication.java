package org.rodeflow.RodeFlowServer;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
public class RodeFlowServerApplication {

	public static void main(String[] args) throws IOException {


		ClassLoader classLoader = RodeFlowServerApplication.class.getClassLoader();
		InputStream serviceAccount = classLoader.getResourceAsStream("rodeflow-1e85e-firebase-adminsdk-acsp9-a48ae60971.json");

		if (serviceAccount == null) {
			throw new FileNotFoundException("Firebase service account file not found.");
		}

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.build();

		FirebaseApp.initializeApp(options);
		SpringApplication.run(RodeFlowServerApplication.class, args);
	}


}
