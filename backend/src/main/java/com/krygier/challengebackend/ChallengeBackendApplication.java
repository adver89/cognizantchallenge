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
public class ChallengeBackendApplication implements CommandLineRunner {

	@Autowired
	private TaskDao taskDao;

	public static void main(String[] args) {
		SpringApplication.run(ChallengeBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("---> app start");
		Task t1 = new Task();
		t1.setName("Task 1");
		t1.setExpectedResult("1 2");
		t1.setDescription("Description 1");
		Task t2 = new Task();
		t2.setName("Task 2");
		t2.setExpectedResult("3 4");
		t2.setDescription("Description 2");
		taskDao.save(t1);
		taskDao.save(t2);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
