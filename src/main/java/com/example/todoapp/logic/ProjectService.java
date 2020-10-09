package com.example.todoapp.logic;

import com.example.todoapp.TaskConfigurationProperties;
import com.example.todoapp.model.*;
import com.example.todoapp.model.projection.GroupReadModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private ProjectRepository repository;
    private TaskGroupRepository taskGroupRepository;
    private TaskConfigurationProperties config;

    public ProjectService(ProjectRepository repository, TaskGroupRepository taskGroupRepository, TaskConfigurationProperties config) {
        this.repository = repository;
        this.taskGroupRepository = taskGroupRepository;
        this.config = config;
    }

    public List<Project> readAll() {
        return repository.findAll();
    }

    public Project save(final Project toSave) {
        return repository.save(toSave);
    }

    public GroupReadModel createGroup(LocalDateTime deadline, int projectId) {
        if (!config.getTemplate().isAllowMultipleTasks() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)) {
            throw new IllegalStateException("Grupa może posiadać tylko jeden projekt");
        }

        TaskGroup result = repository.findById(projectId)
                .map(project -> {
                    var targetGroup = new TaskGroup();
                    targetGroup.setDescription(project.getDescription());

                    targetGroup.setTasks(
                            project.getSteps().stream()
                                    .map(projectStep -> new Task(projectStep.getDescription(),
                                            deadline.plusDays(projectStep.getDaysToDeadline())))
                                    .collect(Collectors.toSet())
                    );
                    return targetGroup;
                }).orElseThrow(() -> new IllegalArgumentException("Project o podanym ID nie istnieje"));
        return new GroupReadModel(result);
    }
}
