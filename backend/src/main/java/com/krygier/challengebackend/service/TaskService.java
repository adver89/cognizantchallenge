package com.krygier.challengebackend.service;

import com.krygier.challengebackend.db.dao.SubmissionDao;
import com.krygier.challengebackend.db.dao.TaskDao;
import com.krygier.challengebackend.db.model.Submission;
import com.krygier.challengebackend.db.model.Task;
import com.krygier.challengebackend.service.mapper.TaskMapper;
import com.krygier.challengebackend.web.model.CompilationRequestDto;
import com.krygier.challengebackend.web.model.SubmissionDto;
import com.krygier.challengebackend.web.model.TaskDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.apache.commons.lang.StringUtils.isEmpty;

@Service
@Log4j2
public class TaskService {

    @Value("${joodle.clientId}")
    private String clientId;
    @Value("${joodle.clientSecret}")
    private String secret;

    @Value("${joodle.url}")
    private String url;
    private static final String DEFAULT_VERSION_INDEX = "0";

    private RestTemplate restTemplate;

    private TaskDao taskDao;

    private SubmissionDao submissionDao;

    @Autowired
    public TaskService(RestTemplate restTemplate, TaskDao taskDao, SubmissionDao submissionDao) {
        this.restTemplate = restTemplate;
        this.taskDao = taskDao;
        this.submissionDao = submissionDao;
    }

    public SubmissionDto submitSolution(SubmissionDto submittedTask) {

        boolean isSuccess = checkSolution(submittedTask);

        Submission submission = Submission.builder()
                .correct(isSuccess)
                .time(LocalDateTime.now())
                .authorName(submittedTask.getAuthor())
                .task(new Task(submittedTask.getTaskId()))
                .build();
        submissionDao.save(submission);
        return SubmissionDto.builder().correct(isSuccess).build();
    }

    public List<TaskDto> getAllTasks() {
        return taskDao.findAll().stream().map(TaskMapper::toDto).collect(Collectors.toList());
    }

    private boolean checkSolution(SubmissionDto submittedTask) {
        return taskDao.findById(submittedTask.getTaskId()).map(entity -> isSolutionCorrect(submittedTask, entity))
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
    }

    private boolean isSolutionCorrect(SubmissionDto task, Task entity) {
        CompilationRequestDto requestBody = getCompilationRequest(task, entity);

        HttpEntity<CompilationRequestDto> request = new HttpEntity<>(requestBody);
        Map<String, String> response = null;
        try {
            response = restTemplate.postForObject(url, request, Map.class, (Object) null);
        } catch (HttpClientErrorException e) {
            log.error("Error during checking solution", e);
            return false;
        }

        if (isNull(response)) {
            return false;
        }
        String compilationOutput = response.get("output");
        if (isEmpty(compilationOutput)) {

            return false;
        }
        return entity.getExpectedResult().equals(compilationOutput.trim());
    }

    private CompilationRequestDto getCompilationRequest(SubmissionDto task, Task entity) {
        CompilationRequestDto requestBody = new CompilationRequestDto();
        requestBody.setStdin(entity.getCodeInput());
        requestBody.setClientId(clientId);
        requestBody.setClientSecret(secret);
        requestBody.setLanguage(task.getLanguage());
        requestBody.setVersionIndex(DEFAULT_VERSION_INDEX);
        requestBody.setScript(task.getCode());
        return requestBody;
    }
}
