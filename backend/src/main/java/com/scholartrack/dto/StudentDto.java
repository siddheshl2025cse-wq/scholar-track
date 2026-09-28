package com.scholartrack.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class StudentDto {
    private Long id;

    @NotBlank(message = "Student name is required")
    private String studentName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Annual income is required")
    @PositiveOrZero(message = "Income cannot be negative")
    private Double annualIncome;

    @NotNull(message = "Marks percentage is required")
    @DecimalMin(value = "0.0", message = "Marks cannot be less than 0")
    @DecimalMax(value = "100.0", message = "Marks cannot be greater than 100")
    private Double marksPercentage;

    @NotBlank(message = "College name is required")
    private String collegeName;

    @NotBlank(message = "Course is required")
    private String course;

    @NotNull(message = "Year of study is required")
    private Integer yearOfStudy;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Double getAnnualIncome() { return annualIncome; }
    public void setAnnualIncome(Double annualIncome) { this.annualIncome = annualIncome; }
    public Double getMarksPercentage() { return marksPercentage; }
    public void setMarksPercentage(Double marksPercentage) { this.marksPercentage = marksPercentage; }
    public String getCollegeName() { return collegeName; }
    public void setCollegeName(String collegeName) { this.collegeName = collegeName; }
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    public Integer getYearOfStudy() { return yearOfStudy; }
    public void setYearOfStudy(Integer yearOfStudy) { this.yearOfStudy = yearOfStudy; }
}
