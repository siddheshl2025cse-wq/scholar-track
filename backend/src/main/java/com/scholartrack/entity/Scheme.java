package com.scholartrack.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "schemes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Scheme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Scheme name is required")
    @Column(nullable = false)
    private String schemeName;

    @NotBlank(message = "Description is required")
    @Column(columnDefinition = "TEXT")
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

    @ElementCollection
    @CollectionTable(name = "scheme_eligible_categories", joinColumns = @JoinColumn(name = "scheme_id"))
    @Column(name = "category")
    private List<String> eligibleCategories = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "scheme_required_documents", joinColumns = @JoinColumn(name = "scheme_id"))
    @Column(name = "document_name")
    private List<String> requiredDocuments = new ArrayList<>();

    @NotNull(message = "Application deadline is required")
    private LocalDate applicationDeadline;

    @NotNull(message = "Scholarship amount is required")
    @PositiveOrZero(message = "Scholarship amount cannot be negative")
    private Double scholarshipAmount;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "scheme")
    private List<Application> applications = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
