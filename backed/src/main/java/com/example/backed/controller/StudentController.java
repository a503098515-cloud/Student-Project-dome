package com.example.backed.controller;
import com.example.backed.entity.Student;
import com.example.backed.service.StudentService;
import com.example.backed.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController// 1. Makes this class a RESTful API handler
@RequestMapping("/api/students")// Sets the base URL for the API: http://localhost:8080/api/students
@CrossOrigin(origins = "*")//Allow React (Frontend) to access this backend

public class StudentController {
    @Autowired// Inject the Repository (Auto-wire)
   private StudentService studentService;

    @GetMapping//  Handles GET requests to Read all students
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping//Handles Post requests to Create a new student
    public Student createStudent(@RequestBody Student student) {
       return studentService.saveStudent(student);
    }

    @PutMapping("/{id}")//Handles PUT requests to Update details
    //@RequestBody is converts JSON to a Java object
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        //use Service function
      Student updatedStudent = studentService.updateStudent(id, studentDetails);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")// Handles DELETE requests to delete a student from id
    public void deleteStudent(@PathVariable Long id) {//Gets the id from the URL
        studentService.deleteStudent(id);
    }
}
