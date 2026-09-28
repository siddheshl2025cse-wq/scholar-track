package com.scholartrack.controller;

import com.scholartrack.dto.ApplicationResponseDto;
import com.scholartrack.entity.Disbursement;
import com.scholartrack.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disbursements")
@Tag(name = "Disbursement", description = "Disbursement processing APIs")
public class DisbursementController {

    private final ApplicationService applicationService;

    public DisbursementController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Operation(summary = "Get all disbursements")
    @GetMapping
    public ResponseEntity<List<ApplicationResponseDto>> getDisbursements() {
        return ResponseEntity.ok(applicationService.getPendingDisbursementApplications());
    }

    @Operation(summary = "Get disbursement for an application")
    @GetMapping("/{applicationId}")
    public ResponseEntity<ApplicationResponseDto> getDisbursement(@PathVariable Long applicationId) {
        return ResponseEntity.ok(applicationService.getApplicationById(applicationId));
    }

    @Operation(summary = "Process disbursement for an approved application")
    @PostMapping("/{applicationId}")
    public ResponseEntity<ApplicationResponseDto> processDisbursement(@PathVariable Long applicationId) {
        return ResponseEntity.ok(applicationService.processDisbursement(applicationId));
    }
}
