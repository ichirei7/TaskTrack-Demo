package com.etms.EmployeeTaskManagementSystem.service;

import com.etms.EmployeeTaskManagementSystem.repository.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemoDataCleanupService {

    private final TimeLogRepository timeLogRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public DemoDataCleanupService(
            TimeLogRepository timeLogRepository,
            ProjectRepository projectRepository,
            TaskRepository taskRepository) {
        this.timeLogRepository = timeLogRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    /**
     * Clean up demo data periodically.
     * You can adjust the schedule as needed.
     * 
     * current setting = every 6 hours
     */
    @Scheduled(cron = "0 0 */6 * * *") // every 6 hours
    @Transactional
    public void cleanDemoData() {
        System.out.println("🧹 Cleaning up demo data...");

        // delete order: children before parents (to prevent foreign key constraint issues)
        timeLogRepository.deleteAllByDemoDataTrue();
        taskRepository.deleteAllByDemoDataTrue();
        projectRepository.deleteAllByDemoDataTrue();

        System.out.println("✅ Demo data cleanup completed.");
    }
}
