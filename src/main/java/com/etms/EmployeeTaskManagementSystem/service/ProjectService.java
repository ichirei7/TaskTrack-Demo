package com.etms.EmployeeTaskManagementSystem.service;

import com.etms.EmployeeTaskManagementSystem.model.Project;
import com.etms.EmployeeTaskManagementSystem.model.User;
import com.etms.EmployeeTaskManagementSystem.repository.ProjectRepository;
import com.etms.EmployeeTaskManagementSystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    private boolean isDemoUser(User user) {
        if (user == null || user.getEmail() == null) return false;
        return user.getEmail().equalsIgnoreCase("manager@demo.com")
            || user.getEmail().equalsIgnoreCase("employee@demo.com");
    }

    /**
     * Create project and automatically mark as demo if created by demo manager.
     */
    public Project createProject(Project project, String managerEmail) {
        User manager = userRepository.findByEmail(managerEmail)
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        project.setManager(manager);

        // mark as demo if manager is a demo user
        if (isDemoUser(manager)) {
            project.setDemoData(true);
        }

        return projectRepository.save(project);
    }

    /**
     * Update project while preserving demo flag.
     */
    public Project updateProject(Project project) {
        Optional<Project> existingProject = projectRepository.findById(project.getId());
        boolean isAlreadyDemo = existingProject.map(Project::isDemoData).orElse(false);

        if (isAlreadyDemo || isDemoUser(project.getManager())) {
            project.setDemoData(true);
        }

        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
