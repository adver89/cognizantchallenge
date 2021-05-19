package com.krygier.challengebackend.web;

import com.krygier.challengebackend.db.dao.TaskDao;
import com.krygier.challengebackend.service.TaskService;
import com.krygier.challengebackend.web.model.SubmissionDto;
import com.krygier.challengebackend.web.model.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDto>> getTasks() {
        List<TaskDto> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/submissions")
    public ResponseEntity<SubmissionDto> submitSolution(@Valid @RequestBody SubmissionDto task) {
        SubmissionDto result = taskService.submitSolution(task);
        return ResponseEntity.ok(result);
    }

}
