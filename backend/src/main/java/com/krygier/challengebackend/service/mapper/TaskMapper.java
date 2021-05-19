package com.krygier.challengebackend.service.mapper;

import com.krygier.challengebackend.db.model.Task;
import com.krygier.challengebackend.web.model.TaskDto;

import static org.springframework.util.ObjectUtils.isEmpty;

public class TaskMapper {

    public static TaskDto toDto(Task task) {
        if (isEmpty(task)) {
            return null;
        }
        TaskDto mapped = new TaskDto();
        mapped.setId(task.getId());
        mapped.setName(task.getName());
        mapped.setDescription(task.getDescription());
        return mapped;
    }
}
