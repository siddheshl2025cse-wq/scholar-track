package com.scholartrack.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SchemeDto {
    private Long id;

    @NotBlank(message = "Scheme name is required")
    private String schemeName;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Provider is required")
    private String provider;

    @NotNull(message = "Income limit is required")
    @PositiveOrZero(message = "Income limit cannot be negative")
    private Double incomeLimit;

    @NotNull(message = "Minimum marks are required")
    @DecimalMin(value = "0.0", message = "Minimum marks cannot be less than 0")
    @DecimalMax(value = "100.0", message = "Minimum marks cannot be greater than 100")
    private Double minimumMarks;

    private List<String> eligibleCategories = new ArrayList<>();
    private List<String> requiredDocuments = new ArrayList<>();

    @NotNull(message = "Application deadline is required")
    private LocalDate applicationDeadline;

    @NotNull(message = "Scholarship amount is required")
    @PositiveOrZero(message = "Scholarship amount cannot be negative")
    private Double scholarshipAmount;

    private boolean active = true;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSchemeName() { return schemeName; }
    public void setSchemeName(String schemeName) { this.schemeName = schemeName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }
    public Double getIncomeLimit() { return incomeLimit; }
    public void setIncomeLimit(Double incomeLimit) { this.incomeLimit = incomeLimit; }
    public Double getMinimumMarks() { return minimumMarks; }
    public void setMinimumMarks(Double minimumMarks) { this.minimumMarks = minimumMarks; }
    public List<String> getEligibleCategories() { return eligibleCategories; }
    public void setEligibleCategories(List<String> eligibleCategories) { this.eligibleCategories = eligibleCategories; }
    public List<String> getRequiredDocuments() { return requiredDocuments; }
    public void setRequiredDocuments(List<String> requiredDocuments) { this.requiredDocuments = requiredDocuments; }
    public LocalDate getApplicationDeadline() { return applicationDeadline; }
    public void setApplicationDeadline(LocalDate applicationDeadline) { this.applicationDeadline = applicationDeadline; }
    public Double getScholarshipAmount() { return scholarshipAmount; }
    public void setScholarshipAmount(Double scholarshipAmount) { this.scholarshipAmount = scholarshipAmount; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
