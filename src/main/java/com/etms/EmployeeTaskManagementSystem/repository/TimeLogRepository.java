package com.etms.EmployeeTaskManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.etms.EmployeeTaskManagementSystem.model.TimeLog;
import java.util.List;

public interface TimeLogRepository extends JpaRepository<TimeLog, Long> {
    List<TimeLog> findByUserId(Long userId);
    List<TimeLog> findByTaskId(Long taskId);
    // to calculate total duration in minutes for a task
    @Query("SELECT SUM(t.duration) FROM TimeLog t WHERE t.task.id = :taskId")
    Integer getTotalDurationByTask(@Param("taskId") Long taskId);
    // use coalesce to avoid returning null 
    @Query("SELECT COALESCE(SUM(t.duration), 0) FROM TimeLog t WHERE t.task.project.id = :projectId")
    Integer getTotalDurationByProject(Long projectId);
    void deleteAllByDemoDataTrue();
}