package com.scholartrack.config;

import com.scholartrack.entity.Application;
import com.scholartrack.entity.Disbursement;
import com.scholartrack.entity.Scheme;
import com.scholartrack.entity.Student;
import com.scholartrack.entity.Verification;
import com.scholartrack.enums.ApplicationStatus;
import com.scholartrack.enums.DisbursementStatus;
import com.scholartrack.enums.DocumentStatus;
import com.scholartrack.enums.EligibilityStatus;
import com.scholartrack.enums.VerificationStatus;
import com.scholartrack.repository.ApplicationRepository;
import com.scholartrack.repository.DisbursementRepository;
import com.scholartrack.repository.SchemeRepository;
import com.scholartrack.repository.StudentRepository;
import com.scholartrack.repository.VerificationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final SchemeRepository schemeRepository;
    private final ApplicationRepository applicationRepository;
    private final VerificationRepository verificationRepository;
    private final DisbursementRepository disbursementRepository;

    public DataSeeder(StudentRepository studentRepository,
                      SchemeRepository schemeRepository,
                      ApplicationRepository applicationRepository,
                      VerificationRepository verificationRepository,
                      DisbursementRepository disbursementRepository) {
        this.studentRepository = studentRepository;
        this.schemeRepository = schemeRepository;
        this.applicationRepository = applicationRepository;
        this.verificationRepository = verificationRepository;
        this.disbursementRepository = disbursementRepository;
    }

    @Override
    public void run(String... args) {
        if (studentRepository.count() > 0) {
            return;
        }

        Student s1 = new Student();
        s1.setStudentName("Aarav Sharma"); s1.setEmail("aarav@gmail.com"); s1.setPhone("9876543210"); s1.setDateOfBirth(LocalDate.of(2004, 5, 15)); s1.setGender("Male"); s1.setCategory("General"); s1.setAnnualIncome(180000.0); s1.setMarksPercentage(88.0); s1.setCollegeName("SRM College"); s1.setCourse("Computer Science"); s1.setYearOfStudy(2);

        Student s2 = new Student();
        s2.setStudentName("Priya Nair"); s2.setEmail("priya@gmail.com"); s2.setPhone("9123456780"); s2.setDateOfBirth(LocalDate.of(2003, 8, 10)); s2.setGender("Female"); s2.setCategory("OBC"); s2.setAnnualIncome(240000.0); s2.setMarksPercentage(76.0); s2.setCollegeName("VIT"); s2.setCourse("Mechanical"); s2.setYearOfStudy(3);

        Student s3 = new Student();
        s3.setStudentName("Rohit Verma"); s3.setEmail("rohit@gmail.com"); s3.setPhone("9988776655"); s3.setDateOfBirth(LocalDate.of(2005, 11, 22)); s3.setGender("Male"); s3.setCategory("SC"); s3.setAnnualIncome(520000.0); s3.setMarksPercentage(91.0); s3.setCollegeName("NIT"); s3.setCourse("Electrical"); s3.setYearOfStudy(2);

        Student s4 = new Student();
        s4.setStudentName("Sneha Iyer"); s4.setEmail("sneha@gmail.com"); s4.setPhone("9654321789"); s4.setDateOfBirth(LocalDate.of(2002, 2, 14)); s4.setGender("Female"); s4.setCategory("General"); s4.setAnnualIncome(180000.0); s4.setMarksPercentage(64.0); s4.setCollegeName("Anna University"); s4.setCourse("Civil"); s4.setYearOfStudy(4);

        Student s5 = new Student();
        s5.setStudentName("Karan Singh"); s5.setEmail("karan@gmail.com"); s5.setPhone("9345678901"); s5.setDateOfBirth(LocalDate.of(2004, 7, 12)); s5.setGender("Male"); s5.setCategory("ST"); s5.setAnnualIncome(290000.0); s5.setMarksPercentage(81.0); s5.setCollegeName("IIT"); s5.setCourse("Electronics"); s5.setYearOfStudy(3);
        studentRepository.saveAll(List.of(s1, s2, s3, s4, s5));

        Scheme scheme1 = new Scheme();
        scheme1.setSchemeName("Merit Scholarship"); scheme1.setDescription("Support gifted students with strong academic performance"); scheme1.setProvider("Government"); scheme1.setIncomeLimit(500000.0); scheme1.setMinimumMarks(80.0); scheme1.setEligibleCategories(Arrays.asList("General", "OBC", "SC", "ST")); scheme1.setRequiredDocuments(Arrays.asList("Marksheet", "Income Certificate")); scheme1.setApplicationDeadline(LocalDate.of(2026, 12, 31)); scheme1.setScholarshipAmount(25000.0); scheme1.setActive(true);

        Scheme scheme2 = new Scheme();
        scheme2.setSchemeName("State Education Scholarship"); scheme2.setDescription("Need-based scholarship for economically weaker students"); scheme2.setProvider("State Education Board"); scheme2.setIncomeLimit(300000.0); scheme2.setMinimumMarks(60.0); scheme2.setEligibleCategories(Arrays.asList("General", "OBC", "SC", "ST")); scheme2.setRequiredDocuments(Arrays.asList("Income Certificate", "Bonafide Certificate")); scheme2.setApplicationDeadline(LocalDate.of(2026, 10, 15)); scheme2.setScholarshipAmount(30000.0); scheme2.setActive(true);

        Scheme scheme3 = new Scheme();
        scheme3.setSchemeName("Institutional Excellence Scholarship"); scheme3.setDescription("Reward for top academic achievers"); scheme3.setProvider("Institutional Office"); scheme3.setIncomeLimit(600000.0); scheme3.setMinimumMarks(85.0); scheme3.setEligibleCategories(Arrays.asList("General", "OBC")); scheme3.setRequiredDocuments(Arrays.asList("Marksheet", "Bonafide Certificate")); scheme3.setApplicationDeadline(LocalDate.of(2026, 11, 20)); scheme3.setScholarshipAmount(45000.0); scheme3.setActive(true);

        Scheme scheme4 = new Scheme();
        scheme4.setSchemeName("First Generation Graduate Scholarship"); scheme4.setDescription("Support first generation graduates"); scheme4.setProvider("National Education Trust"); scheme4.setIncomeLimit(250000.0); scheme4.setMinimumMarks(60.0); scheme4.setEligibleCategories(Arrays.asList("General", "OBC", "SC", "ST")); scheme4.setRequiredDocuments(Arrays.asList("Community Certificate", "Income Certificate")); scheme4.setApplicationDeadline(LocalDate.of(2026, 9, 30)); scheme4.setScholarshipAmount(20000.0); scheme4.setActive(true);

        Scheme scheme5 = new Scheme();
        scheme5.setSchemeName("Technical Education Scholarship"); scheme5.setDescription("Scholarship for technical disciplines"); scheme5.setProvider("Ministry of Education"); scheme5.setIncomeLimit(400000.0); scheme5.setMinimumMarks(70.0); scheme5.setEligibleCategories(Arrays.asList("General", "OBC", "SC", "ST")); scheme5.setRequiredDocuments(Arrays.asList("Marksheet", "Bank Passbook")); scheme5.setApplicationDeadline(LocalDate.of(2026, 12, 10)); scheme5.setScholarshipAmount(35000.0); scheme5.setActive(true);
        schemeRepository.saveAll(List.of(scheme1, scheme2, scheme3, scheme4, scheme5));

        Application app1 = new Application(); app1.setApplicationNumber("ST-1001"); app1.setStudent(s1); app1.setScheme(scheme1); app1.setApplicationDate(LocalDate.now().minusDays(6)); app1.setEligibilityStatus(EligibilityStatus.ELIGIBLE); app1.setApplicationStatus(ApplicationStatus.PENDING_VERIFICATION); app1.setSubmitted(true); app1.setRemarks("Ready for verification");
        Application app2 = new Application(); app2.setApplicationNumber("ST-1002"); app2.setStudent(s2); app2.setScheme(scheme4); app2.setApplicationDate(LocalDate.now().minusDays(8)); app2.setEligibilityStatus(EligibilityStatus.INELIGIBLE); app2.setApplicationStatus(ApplicationStatus.INELIGIBLE); app2.setSubmitted(true); app2.setRemarks("Income not eligible");
        Application app3 = new Application(); app3.setApplicationNumber("ST-1003"); app3.setStudent(s3); app3.setScheme(scheme2); app3.setApplicationDate(LocalDate.now().minusDays(3)); app3.setEligibilityStatus(EligibilityStatus.ELIGIBLE); app3.setApplicationStatus(ApplicationStatus.APPROVED); app3.setSubmitted(true); app3.setRemarks("Approved and waiting for disbursement");
        Application app4 = new Application(); app4.setApplicationNumber("ST-1004"); app4.setStudent(s4); app4.setScheme(scheme5); app4.setApplicationDate(LocalDate.now().minusDays(10)); app4.setEligibilityStatus(EligibilityStatus.ELIGIBLE); app4.setApplicationStatus(ApplicationStatus.REJECTED); app4.setSubmitted(true); app4.setRemarks("Documents incomplete");
        Application app5 = new Application(); app5.setApplicationNumber("ST-1005"); app5.setStudent(s5); app5.setScheme(scheme3); app5.setApplicationDate(LocalDate.now().minusDays(1)); app5.setEligibilityStatus(EligibilityStatus.ELIGIBLE); app5.setApplicationStatus(ApplicationStatus.DISBURSED); app5.setSubmitted(true); app5.setRemarks("Fully disbursed");
        applicationRepository.saveAll(List.of(app1, app2, app3, app4, app5));

        Verification v1 = new Verification(); v1.setApplication(app1); v1.setVerifierName("Verifier A"); v1.setVerificationStatus(VerificationStatus.PENDING); v1.setRemarks("Awaiting review"); v1.setVerifiedAt(null); v1.setDocumentStatus(DocumentStatus.PENDING);
        Verification v2 = new Verification(); v2.setApplication(app3); v2.setVerifierName("Verifier B"); v2.setVerificationStatus(VerificationStatus.APPROVED); v2.setRemarks("Approved after document check"); v2.setVerifiedAt(LocalDate.now().minusDays(2).atStartOfDay()); v2.setDocumentStatus(DocumentStatus.VERIFIED);
        Verification v3 = new Verification(); v3.setApplication(app4); v3.setVerifierName("Verifier C"); v3.setVerificationStatus(VerificationStatus.REJECTED); v3.setRemarks("Missing bank passbook"); v3.setVerifiedAt(LocalDate.now().minusDays(4).atStartOfDay()); v3.setDocumentStatus(DocumentStatus.REJECTED);
        verificationRepository.saveAll(List.of(v1, v2, v3));

        app1.setVerification(v1); app3.setVerification(v2); app4.setVerification(v3);
        applicationRepository.saveAll(List.of(app1, app3, app4));

        Disbursement d1 = new Disbursement(); d1.setApplication(app3); d1.setAmount(30000.0); d1.setDisbursementStatus(DisbursementStatus.PENDING); d1.setTransactionReference("ST-2026-000001"); d1.setDisbursementDate(LocalDate.now()); d1.setRemarks("Disbursement pending");
        Disbursement d2 = new Disbursement(); d2.setApplication(app5); d2.setAmount(45000.0); d2.setDisbursementStatus(DisbursementStatus.DISBURSED); d2.setTransactionReference("ST-2026-000002"); d2.setDisbursementDate(LocalDate.now().minusDays(1)); d2.setRemarks("Payment transferred");
        disbursementRepository.saveAll(List.of(d1, d2));

        app3.setDisbursement(d1); app5.setDisbursement(d2);
        applicationRepository.saveAll(List.of(app3, app5));
    }
}
