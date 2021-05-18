package com.krygier.challengebackend.web;

import com.krygier.challengebackend.db.dao.TaskDao;
import com.krygier.challengebackend.db.model.Submission;
import com.krygier.challengebackend.db.model.Task;
import com.krygier.challengebackend.service.TaskService;
import com.krygier.challengebackend.web.model.SubmissionDto;
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
    public ResponseEntity<List<Task>> getTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/submissions")
    public ResponseEntity<Submission> submitSolution(@Valid @RequestBody SubmissionDto task) {
        Submission result = taskService.submitTask(task);
        return ResponseEntity.ok(result);
    }

}
