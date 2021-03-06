package com.krygier.challengebackend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krygier.challengebackend.db.dao.SubmissionDao;
import com.krygier.challengebackend.db.dao.TaskDao;
import com.krygier.challengebackend.db.model.Task;
import com.krygier.challengebackend.web.model.SubmissionDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SubmissionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private SubmissionDao submissionDao;

    private Task createdTask01;
    private Task createdTask02;

    @BeforeAll
    public void setUp() {
        Task task01 = new Task();
        task01.setName("Task01");
        task01.setExpectedResult("2 4");
        createdTask01 = taskDao.save(task01);

        Task task02 = new Task();
        task02.setName("Task01");
        task02.setExpectedResult("4 5 6");
        createdTask02 = taskDao.save(task02);
    }

    @SneakyThrows
    @Test
    public void correctSolution_returns200AndCorrectTrue() {

        String requestBody = getSampleSolution(createdTask01.getId());

        mockMvc.perform(post("/api/submissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correct").value("true"));

    }

    @SneakyThrows
    @Test
    public void wrongSolution_returns200AndCorrectFalse() {

        String requestBody = getSampleSolution(createdTask02.getId());

        mockMvc.perform(post("/api/submissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correct").value("false"));
    }

    @SneakyThrows
    @Test
    public void notValidInput_returns400() {

        String notValidRequestBody = getSampleSolution(null);

        String result = mockMvc.perform(post("/api/submissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(notValidRequestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Request body not valid"))
                .andReturn().getResponse().getContentAsString();

    }

    @SneakyThrows
    private String getSampleSolution(Long taskId) {
        SubmissionDto solution = new SubmissionDto();
        solution.setTaskId(taskId);
        solution.setAuthor("Player01");
        solution.setLanguage("nodejs");
        solution.setCode("console.log('2 4');");

        return new ObjectMapper().writeValueAsString(solution);
    }
}
