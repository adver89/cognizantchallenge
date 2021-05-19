package com.krygier.challengebackend;

import com.krygier.challengebackend.db.dao.TaskDao;
import com.krygier.challengebackend.db.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ChallengeBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeBackendApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
