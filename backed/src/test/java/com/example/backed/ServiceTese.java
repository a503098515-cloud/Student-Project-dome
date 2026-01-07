package com.example.backed;
import com.example.backed.entity.Student;
import com.example.backed.repository.StudentRepository;
import com.example.backed.service.StudentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)//Automatically initialize Mock objects

class ServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentService studentService;
    @DisplayName("Testing whether the GPA has been saved")
       @ParameterizedTest(name = "Should save student with valid GPA: {0}")
       @ValueSource(doubles = {3.5, 0.0, 4.0}) 
       void saveStudent_Success_Parameterized(double validGpa) {
        Student student = new Student();
        student.setFirstName("John");
        student.setGpa(validGpa);
        when(studentRepository.save(student)).thenReturn(student);
        Student result = studentService.saveStudent(student);
        // verification
        assertEquals(validGpa, result.getGpa());
        verify(studentRepository).save(student);
    }
    @DisplayName("Test whether the gpa is greater then 4.0 or is a negative number")
    @ParameterizedTest(name = "Should throw exception for invalid GPA: {0}")
    @ValueSource(doubles = {5.0, 4.1, -1.0, -0.1}) 
    void saveStudent_Fail_Parameterized(double invalidGpa) {
        Student student = new Student();
        student.setFirstName("Hacker");
        student.setGpa(invalidGpa);
       // verification
        Exception e = assertThrows(RuntimeException.class, () -> {
            studentService.saveStudent(student);
        });
        assertEquals("Invalid GPA: GPA must be between 0 and 4.0", e.getMessage());
        verify(studentRepository, never()).save(any());
    }
}
