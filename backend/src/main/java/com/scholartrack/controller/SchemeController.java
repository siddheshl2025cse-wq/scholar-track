package com.scholartrack.controller;

import com.scholartrack.dto.SchemeDto;
import com.scholartrack.service.SchemeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schemes")
@Tag(name = "Scholarships", description = "Scholarship scheme APIs")
public class SchemeController {

    private final SchemeService schemeService;

    public SchemeController(SchemeService schemeService) {
        this.schemeService = schemeService;
    }

    @Operation(summary = "Create a scholarship scheme")
    @PostMapping
    public ResponseEntity<SchemeDto> createScheme(@Valid @RequestBody SchemeDto schemeDto) {
        return new ResponseEntity<>(schemeService.createScheme(schemeDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all scholarship schemes")
    @GetMapping
    public ResponseEntity<List<SchemeDto>> getAllSchemes() {
        return ResponseEntity.ok(schemeService.getAllSchemes());
    }

    @Operation(summary = "Get scheme by id")
    @GetMapping("/{id}")
    public ResponseEntity<SchemeDto> getSchemeById(@PathVariable Long id) {
        return ResponseEntity.ok(schemeService.getSchemeById(id));
    }

    @Operation(summary = "Update a scholarship scheme")
    @PutMapping("/{id}")
    public ResponseEntity<SchemeDto> updateScheme(@PathVariable Long id, @Valid @RequestBody SchemeDto schemeDto) {
        return ResponseEntity.ok(schemeService.updateScheme(id, schemeDto));
    }

    @Operation(summary = "Delete a scholarship scheme")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScheme(@PathVariable Long id) {
        schemeService.deleteScheme(id);
        return ResponseEntity.noContent().build();
    }
}
