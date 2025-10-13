package com.etms.EmployeeTaskManagementSystem.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etms.EmployeeTaskManagementSystem.service.DemoDataCleanupService;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoDataCleanupService cleanupService;

    public DemoController(DemoDataCleanupService cleanupService) {
        this.cleanupService = cleanupService;
    }

    @DeleteMapping("/cleanup")
    public String cleanupNow() {
        cleanupService.cleanDemoData();
        return "Demo data cleaned manually.";
    }
}
