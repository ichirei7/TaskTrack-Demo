package com.etms.EmployeeTaskManagementSystem.service;

import com.etms.EmployeeTaskManagementSystem.model.Task;
import com.etms.EmployeeTaskManagementSystem.model.TaskStatus;
import com.etms.EmployeeTaskManagementSystem.model.User;
import com.etms.EmployeeTaskManagementSystem.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    
    private boolean isDemoUser(User user) {
        if (user == null || user.getEmail() == null) return false;
        return user.getEmail().equalsIgnoreCase("manager@demo.com")
            || user.getEmail().equalsIgnoreCase("employee@demo.com");
    }
    
    public Task createTask(Task task) {
        if (task.getAssignedTo() != null && isDemoUser(task.getAssignedTo())) {
            task.setDemoData(true);
        }
        // If the project itself is a demo project, mark this task too
        if (task.getProject() != null && task.getProject().isDemoData()) {
            task.setDemoData(true);
        }

        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }
    
    public List <Task> getTasksByProjectId(Long projectId) {
    	return taskRepository.findByProjectId(projectId);
    }
    
    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    public List<Task> getTasksByUser(Long userId) {
        return taskRepository.findByAssignedToId(userId);
    }


    public Task updateTask(Task task) {
    	// Fetch the existing task from DB first
        Optional<Task> existingTask = taskRepository.findById(task.getId());
        
        // If this task was already demo, keep it demo
        boolean isAlreadyDemo = existingTask.map(Task::isDemoData).orElse(false);

        // Mark as demo only if either already demo or edited by demo user
        if (isAlreadyDemo 
            || (task.getAssignedTo() != null && isDemoUser(task.getAssignedTo())) 
            || (task.getProject() != null && task.getProject().isDemoData())) {
            task.setDemoData(true);
        }
    	    
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
    	
        taskRepository.deleteById(id);
    }
}
