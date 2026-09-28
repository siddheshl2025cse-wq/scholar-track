package com.scholartrack.dto;

import jakarta.validation.constraints.NotNull;

public class ApplicationRequestDto {
    @NotNull(message = "Student id is required")
    private Long studentId;

    @NotNull(message = "Scheme id is required")
    private Long schemeId;

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public Long getSchemeId() { return schemeId; }
    public void setSchemeId(Long schemeId) { this.schemeId = schemeId; }
}
