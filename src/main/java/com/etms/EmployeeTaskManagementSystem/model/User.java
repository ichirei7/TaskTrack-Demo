package com.etms.EmployeeTaskManagementSystem.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String email;
    private String password;
    
    //Stores role values as string in db
    @Enumerated(EnumType.STRING)
    private Role role;
    
    @Column(nullable = false)
    private boolean demoData = false;
    
    //Automatic timestamp on creation
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @OneToMany(mappedBy = "assignedTo")
    @JsonIgnoreProperties({"assignedTo", "project", "timeLogs"})
    private List<Task> tasks;

    @OneToMany(mappedBy = "manager")
    @JsonIgnoreProperties({"manager", "tasks"})
    private List<Project> managedProjects;
    
    public User() {
		// TODO Auto-generated constructor stub
	}
	public User(Long id) {
		this.id = id;
	}

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public List<Project> getManagedProjects() {
		return managedProjects;
	}

	public void setManagedProjects(List<Project> managedProjects) {
		this.managedProjects = managedProjects;
	}
	public boolean isDemoData() {
		return demoData;
	}
	public void setDemoData(boolean demoData) {
		this.demoData = demoData;
	}
    
	
    
}
