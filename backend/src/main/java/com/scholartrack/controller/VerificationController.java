package com.scholartrack.controller;

import com.scholartrack.dto.ApplicationResponseDto;
import com.scholartrack.dto.VerificationRequestDto;
import com.scholartrack.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/verifications")
@Tag(name = "Verification", description = "Scholarship verification APIs")
public class VerificationController {

    private final ApplicationService applicationService;

    public VerificationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Operation(summary = "Get pending verification applications")
    @GetMapping("/pending")
    public ResponseEntity<List<ApplicationResponseDto>> getPendingVerifications() {
        return ResponseEntity.ok(applicationService.getPendingVerifications());
    }

    @Operation(summary = "Approve an application")
    @PostMapping("/{applicationId}/approve")
    public ResponseEntity<ApplicationResponseDto> approveApplication(@PathVariable Long applicationId,
                                                                   @RequestParam(defaultValue = "Verifier") String verifierName,
                                                                   @Valid @RequestBody VerificationRequestDto request) {
        return ResponseEntity.ok(applicationService.approveApplication(applicationId, verifierName, request.getRemarks()));
    }

    @Operation(summary = "Reject an application")
    @PostMapping("/{applicationId}/reject")
    public ResponseEntity<ApplicationResponseDto> rejectApplication(@PathVariable Long applicationId,
                                                                   @RequestParam(defaultValue = "Verifier") String verifierName,
                                                                   @Valid @RequestBody VerificationRequestDto request) {
        return ResponseEntity.ok(applicationService.rejectApplication(applicationId, verifierName, request.getRemarks()));
    }
}
