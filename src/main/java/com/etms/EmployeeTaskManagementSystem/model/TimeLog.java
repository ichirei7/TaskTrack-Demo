package com.etms.EmployeeTaskManagementSystem.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="time_logs")
public class TimeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties({"timeLogs", "assignedTo"})
    @JsonIncludeProperties({"id", "project"})
    private Task task;

    @ManyToOne
    @JsonIgnoreProperties({"managedProjects", "tasks"})
    private User user; // Employee logging time

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer duration; // in minutes or hours
    private String notes;
    
    @Column(nullable = false)
    private boolean demoData = false;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Task getTask() { return task; }
    public void setTask(Task task) { this.task = task; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
	public boolean isDemoData() {
		return demoData;
	}
	public void setDemoData(boolean demoData) {
		this.demoData = demoData;
	}
    
    
}
