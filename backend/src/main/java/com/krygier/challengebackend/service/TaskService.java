package com.krygier.challengebackend.service;

import com.krygier.challengebackend.db.dao.SubmissionDao;
import com.krygier.challengebackend.db.dao.TaskDao;
import com.krygier.challengebackend.db.model.Submission;
import com.krygier.challengebackend.db.model.Task;
import com.krygier.challengebackend.web.model.CompilationRequestDto;
import com.krygier.challengebackend.web.model.SubmissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.isEmpty;

@Service
public class TaskService {

    @Value("${joodle.clientId}")
    private String clientId;
    @Value("${joodle.clientSecret}")
    private String secret;

    @Value("${joodle.url}")
    private String url;
    private static final String DEFAULT_VERSION_INDEX = "0";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private SubmissionDao submissionDao;

    public Submission submitTask(SubmissionDto task) {

        boolean isSuccess = checkSolution(task);

        Submission submission = Submission.builder()
                .correct(isSuccess)
                .time(LocalDateTime.now())
                .authorName(task.getAuthor())
                .task(new Task(task.getTaskId()))
                .build();
        return submissionDao.save(submission);
    }

    private boolean checkSolution(SubmissionDto task) {
        return taskDao.findById(task.getTaskId()).map(entity -> {

            CompilationRequestDto requestBody = new CompilationRequestDto();
            requestBody.setStdin(entity.getCodeInput());
            requestBody.setClientId(clientId);
            requestBody.setClientSecret(secret);
            requestBody.setLanguage(task.getLanguage());
            requestBody.setVersionIndex(DEFAULT_VERSION_INDEX);
            requestBody.setScript(task.getCode());

            HttpEntity<CompilationRequestDto> request = new HttpEntity<>(requestBody);
            Map<String, String> response = null;
            try {
                response = restTemplate.postForObject(url, request, Map.class, (Object) null);
            } catch (HttpClientErrorException e) {
                // TODO
                return false;
            }

            String compilationOutput = response.get("output");
            if (isEmpty(compilationOutput)) {
                // TODO
                return false;
            }
            return entity.getExpectedResult().equals(compilationOutput.trim());
        }).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public List<Task> getAllTasks() {
        return taskDao.findAll();
    }
}
