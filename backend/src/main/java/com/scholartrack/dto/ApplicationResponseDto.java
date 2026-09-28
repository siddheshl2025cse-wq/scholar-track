package com.scholartrack.dto;

import com.scholartrack.enums.ApplicationStatus;
import com.scholartrack.enums.EligibilityStatus;
import java.time.LocalDate;

public class ApplicationResponseDto {
    private Long id;
    private String applicationNumber;
    private Long studentId;
    private String studentName;
    private Long schemeId;
    private String schemeName;
    private LocalDate applicationDate;
    private EligibilityStatus eligibilityStatus;
    private ApplicationStatus applicationStatus;
    private boolean submitted;
    private String remarks;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getApplicationNumber() { return applicationNumber; }
    public void setApplicationNumber(String applicationNumber) { this.applicationNumber = applicationNumber; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public Long getSchemeId() { return schemeId; }
    public void setSchemeId(Long schemeId) { this.schemeId = schemeId; }
    public String getSchemeName() { return schemeName; }
    public void setSchemeName(String schemeName) { this.schemeName = schemeName; }
    public LocalDate getApplicationDate() { return applicationDate; }
    public void setApplicationDate(LocalDate applicationDate) { this.applicationDate = applicationDate; }
    public EligibilityStatus getEligibilityStatus() { return eligibilityStatus; }
    public void setEligibilityStatus(EligibilityStatus eligibilityStatus) { this.eligibilityStatus = eligibilityStatus; }
    public ApplicationStatus getApplicationStatus() { return applicationStatus; }
    public void setApplicationStatus(ApplicationStatus applicationStatus) { this.applicationStatus = applicationStatus; }
    public boolean isSubmitted() { return submitted; }
    public void setSubmitted(boolean submitted) { this.submitted = submitted; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}
