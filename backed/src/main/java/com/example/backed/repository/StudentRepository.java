package com.example.backed.repository;

import com.example.backed.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

@Repository//Handles database operations
public interface StudentRepository extends JpaRepository<Student, Long> {
}