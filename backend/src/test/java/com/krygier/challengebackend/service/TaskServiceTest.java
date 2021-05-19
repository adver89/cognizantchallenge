package com.krygier.challengebackend.service;

import com.krygier.challengebackend.db.dao.SubmissionDao;
import com.krygier.challengebackend.db.dao.TaskDao;
import com.krygier.challengebackend.db.model.Submission;
import com.krygier.challengebackend.db.model.Task;
import com.krygier.challengebackend.web.model.CompilationRequestDto;
import com.krygier.challengebackend.web.model.SubmissionDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaskServiceTest {

    private TaskService taskService;

    private RestTemplate restTemplate;

    private TaskDao taskDao;

    private SubmissionDao submissionDao;

    @BeforeAll
    public void setUp() {
        restTemplate = mock(RestTemplate.class);
        taskDao = mock(TaskDao.class);
        submissionDao = mock(SubmissionDao.class);
        taskService = new TaskService(restTemplate, taskDao, submissionDao);


        Task task01 = new Task();
        task01.setExpectedResult("1 2");
        task01.setId(1L);
        when(taskDao.findById(eq(1L))).thenReturn(ofNullable(task01));

    }

    @Test
    public void submitSolution_correctResponse_savesRecordToDb() {

        // given
        CompilationRequestDto requestBody = new CompilationRequestDto();

        Map<String, String> resultMap = Collections.singletonMap("output", "1 2");
        when(restTemplate.postForObject(any(), any(), any(), (Object) eq(null))).thenReturn(resultMap);

        SubmissionDto solution = new SubmissionDto();
        solution.setTaskId(1L);

        // when
        SubmissionDto result = taskService.submitSolution(solution);

        // then
        assertThat(result.getCorrect()).isTrue();

        verify(restTemplate, times(1)).postForObject(any(), any(), any(), (Object) any());
        verify(submissionDao, times(1)).save(any(Submission.class));
    }

    @Test
    public void submitSolution_wrongResponse_doesNothing() {

        // given
        CompilationRequestDto requestBody = new CompilationRequestDto();
        HttpEntity<CompilationRequestDto> request = new HttpEntity<>(requestBody);

        Map<String, String> resultMap = Collections.singletonMap("output", "3 4");
        when(restTemplate.postForObject(any(), any(), any(), (Object) eq(null))).thenReturn(resultMap);

        SubmissionDto solution = new SubmissionDto();
        solution.setTaskId(1L);
        reset(submissionDao);

        // when
        SubmissionDto result = taskService.submitSolution(solution);

        // then
        assertThat(result.getCorrect()).isFalse();

        verify(submissionDao, times(1)).save(any(Submission.class));
    }
}
