package com.scholartrack.controller;

import com.scholartrack.dto.ApplicationRequestDto;
import com.scholartrack.dto.ApplicationResponseDto;
import com.scholartrack.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@Tag(name = "Applications", description = "Application lifecycle APIs")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Operation(summary = "Submit scholarship application")
    @PostMapping
    public ResponseEntity<ApplicationResponseDto> submitApplication(@Valid @RequestBody ApplicationRequestDto request) {
        return new ResponseEntity<>(applicationService.submitApplication(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all applications")
    @GetMapping
    public ResponseEntity<List<ApplicationResponseDto>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @Operation(summary = "Get application details")
    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponseDto> getApplicationById(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }

    @Operation(summary = "Get applications for a student")
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ApplicationResponseDto>> getApplicationsByStudentId(@PathVariable Long studentId) {
        return ResponseEntity.ok(applicationService.getApplicationsByStudentId(studentId));
    }

    @Operation(summary = "Get current application status")
    @GetMapping("/{id}/status")
    public ResponseEntity<ApplicationResponseDto> getApplicationStatus(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.getApplicationStatus(id));
    }
}
