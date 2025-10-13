package com.etms.EmployeeTaskManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.etms.EmployeeTaskManagementSystem.model.Task;
import com.etms.EmployeeTaskManagementSystem.model.TaskStatus;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);  // get all tasks by status
    List<Task> findByAssignedToId(Long userId);  // get all tasks assigned to a user
    List<Task> findByProjectId(Long projectId); //get tasks by projectId
    void deleteAllByDemoDataTrue();
}