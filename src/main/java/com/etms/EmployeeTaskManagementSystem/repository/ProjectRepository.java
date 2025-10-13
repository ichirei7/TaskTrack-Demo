package com.etms.EmployeeTaskManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.etms.EmployeeTaskManagementSystem.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
	void deleteAllByDemoDataTrue();
}