package com.etms.EmployeeTaskManagementSystem.controller;

import com.etms.EmployeeTaskManagementSystem.dto.ProjectRequest;
import com.etms.EmployeeTaskManagementSystem.model.Project;
import com.etms.EmployeeTaskManagementSystem.security.CustomUserDetails;
import com.etms.EmployeeTaskManagementSystem.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

//    @PostMapping
//    public ResponseEntity<Project> createProject(@RequestBody Project project) {
//        return ResponseEntity.ok(projectService.createProject(project));
//    }
    
//    @PostMapping
//    public ResponseEntity<Project> createProject(@RequestBody Project project,
//                                                 @AuthenticationPrincipal CustomUserDetails userDetails) {
//        String email = userDetails.getUsername(); // assuming your CustomUserDetails stores email as username
//        Project savedProject = projectService.createProject(project, email);
//        return ResponseEntity.ok(savedProject);
//    }
    
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody ProjectRequest request,
                                                 @AuthenticationPrincipal CustomUserDetails userDetails) {
        String email = userDetails.getUsername();

        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());

        Project savedProject = projectService.createProject(project, email);
        return ResponseEntity.ok(savedProject);
    }


    
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
//    @PutMapping("/{id}")
//    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project project) {
//        return projectService.getProjectById(id)
//                .map(existing -> {
//                    project.setId(id);
//                    return ResponseEntity.ok(projectService.createProject(project));
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project project) {
        Optional<Project> existingOpt = projectService.getProjectById(id);

        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Project existing = existingOpt.get();
        existing.setName(project.getName());
        existing.setDescription(project.getDescription());
        // Manager stays the same
        existing.setManager(existing.getManager());

        Project updated = projectService.updateProject(existing);
        return ResponseEntity.ok(updated);
    }

}
