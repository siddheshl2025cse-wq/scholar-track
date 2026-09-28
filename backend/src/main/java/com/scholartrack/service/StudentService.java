package com.scholartrack.service;

import com.scholartrack.dto.StudentDto;
import com.scholartrack.entity.Student;
import com.scholartrack.exception.ResourceNotFoundException;
import com.scholartrack.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentDto createStudent(@Valid StudentDto dto) {
        Student student = mapToEntity(dto);
        Student saved = studentRepository.save(student);
        return mapToDto(saved);
    }

    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        return mapToDto(student);
    }

    public StudentDto updateStudent(Long id, @Valid StudentDto dto) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));

        existing.setStudentName(dto.getStudentName());
        existing.setEmail(dto.getEmail());
        existing.setPhone(dto.getPhone());
        existing.setDateOfBirth(dto.getDateOfBirth());
        existing.setGender(dto.getGender());
        existing.setCategory(dto.getCategory());
        existing.setAnnualIncome(dto.getAnnualIncome());
        existing.setMarksPercentage(dto.getMarksPercentage());
        existing.setCollegeName(dto.getCollegeName());
        existing.setCourse(dto.getCourse());
        existing.setYearOfStudy(dto.getYearOfStudy());

        return mapToDto(studentRepository.save(existing));
    }

    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        studentRepository.delete(student);
    }

    private Student mapToEntity(StudentDto dto) {
        Student student = new Student();
        student.setId(dto.getId());
        student.setStudentName(dto.getStudentName());
        student.setEmail(dto.getEmail());
        student.setPhone(dto.getPhone());
        student.setDateOfBirth(dto.getDateOfBirth());
        student.setGender(dto.getGender());
        student.setCategory(dto.getCategory());
        student.setAnnualIncome(dto.getAnnualIncome());
        student.setMarksPercentage(dto.getMarksPercentage());
        student.setCollegeName(dto.getCollegeName());
        student.setCourse(dto.getCourse());
        student.setYearOfStudy(dto.getYearOfStudy());
        return student;
    }

    private StudentDto mapToDto(Student student) {
        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setStudentName(student.getStudentName());
        dto.setEmail(student.getEmail());
        dto.setPhone(student.getPhone());
        dto.setDateOfBirth(student.getDateOfBirth());
        dto.setGender(student.getGender());
        dto.setCategory(student.getCategory());
        dto.setAnnualIncome(student.getAnnualIncome());
        dto.setMarksPercentage(student.getMarksPercentage());
        dto.setCollegeName(student.getCollegeName());
        dto.setCourse(student.getCourse());
        dto.setYearOfStudy(student.getYearOfStudy());
        return dto;
    }
}
