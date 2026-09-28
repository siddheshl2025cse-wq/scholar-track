package com.scholartrack.dto;

import jakarta.validation.constraints.NotBlank;

public class VerificationRequestDto {
    @NotBlank(message = "Remarks are required")
    private String remarks;

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}
