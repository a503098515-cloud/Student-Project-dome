package com.example.backed.service;
import com.example.backed.entity.Student;
import com.example.backed.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    // Save and Check if the Studen data is not empty
    public Student saveStudent(Student student) {
        if (student == null) {
        throw new IllegalArgumentException("Student cannot be null");
    }
        // Check if GPA data is valid
        if (student.getGpa() < 0 || student.getGpa() > 4.0) {
    throw new IllegalArgumentException("Invalid GPA: Must be between 0.0 and 4.0");
}
        return studentRepository.save(student);
    }
    // Get 
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    // Update 
    public Student updateStudent(Long id, Student studentDetails) {
        Student student = getStudentById(id);
        
        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setEmail(studentDetails.getEmail());
        student.setGpa(studentDetails.getGpa());
        
        return studentRepository.save(student);
    }

    // Delete 
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}


