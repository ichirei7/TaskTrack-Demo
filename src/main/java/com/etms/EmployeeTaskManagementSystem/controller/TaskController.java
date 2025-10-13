package com.etms.EmployeeTaskManagementSystem.controller;

import com.etms.EmployeeTaskManagementSystem.model.Task;
import com.etms.EmployeeTaskManagementSystem.model.TaskStatus;
import com.etms.EmployeeTaskManagementSystem.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.createTask(task));
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable TaskStatus status) {
        return ResponseEntity.ok(taskService.getTasksByStatus(status));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getTasksByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getTasksByUser(userId));
    }
    
    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<Task>> getTasksByProject(@PathVariable Long id){
    	List<Task> tasks = taskService.getTasksByProjectId(id);
    	return ResponseEntity.ok(tasks);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task incomingTask) {
    	
    	return taskService.getTaskById(id)
            .map(existingTask -> {
                // Detect status change to DONE
            	boolean isNewlyCompleted = existingTask.getStatus() != TaskStatus.DONE &&
                        incomingTask.getStatus() == TaskStatus.DONE;


                // Apply updates
                existingTask.setTitle(incomingTask.getTitle());
                existingTask.setDescription(incomingTask.getDescription());
                existingTask.setDueDate(incomingTask.getDueDate());
                existingTask.setStatus(incomingTask.getStatus());
                existingTask.setAssignedTo(incomingTask.getAssignedTo());
                existingTask.setProject(incomingTask.getProject());

                if (isNewlyCompleted) {
                    existingTask.setCompletedAt(LocalDateTime.now());
                }

                return ResponseEntity.ok(taskService.updateTask(existingTask));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
