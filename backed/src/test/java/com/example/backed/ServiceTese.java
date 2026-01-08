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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentService studentService;
//Test saveStudent
    @Test
    public void saveStudent_Fail_WhenStudentIsNull() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
        studentService.saveStudent(null);});
    assertEquals("Student cannot be null", e.getMessage());
}

//Test getAllStudents
    @Test
    public void getAllStudents_ShouldReturnList() {
        Student s1 = new Student(); s1.setFirstName("A");
        Student s2 = new Student(); s2.setFirstName("B");
        // return 2 object
        when(studentRepository.findAll()).thenReturn(Arrays.asList(s1, s2));
        List<Student> result = studentService.getAllStudents();
        assertEquals(2, result.size()); 
        verify(studentRepository).findAll();}

//Test getStudentById
   @Test
    public void getStudentById_Success() {
        Student s = new Student();
        s.setId(1L);
        // Find Mockito return Optional.of(...)
        when(studentRepository.findById(1L)).thenReturn(Optional.of(s));
        Student result = studentService.getStudentById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

// Test NotFound ID
    @Test
    public void getStudentById_NotFound() {
        // No find Mockito return Empty
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());
        RuntimeException e = assertThrows(RuntimeException.class, () -> {
            studentService.getStudentById(99L);
        });
        assertEquals("Student not found", e.getMessage());
    }

//Test updateStudent
    @Test 
    public void updateStudent_Success() {
        //old data
        Student existingStudent = new Student();
        existingStudent.setId(1L);
        existingStudent.setFirstName("OldName");
        //new data
        Student newDetails = new Student();
        newDetails.setFirstName("NewName");
        newDetails.setGpa(4.0);
        // If findById works correctly
        when(studentRepository.findById(1L)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(existingStudent)).thenReturn(existingStudent);
        Student updated = studentService.updateStudent(1L, newDetails);
        assertEquals("NewName", updated.getFirstName());
        verify(studentRepository).save(existingStudent);
    }
//Test deleteStudent
    @Test
    public void deleteStudent_Success() {
        Long studentId = 1L;
        studentService.deleteStudent(studentId);
        verify(studentRepository, times(1)).deleteById(studentId);
    }

//Test GPA save
    @DisplayName("Testing whether the GPA has been saved")
    @ParameterizedTest(name = "Should save student with valid GPA: {0}")
    @ValueSource(doubles = {3.5, 0.0, 4.0}) 
    public void saveStudent_Success_Parameterized(double validGpa) {
        Student student = new Student();
        student.setFirstName("John");
        student.setGpa(validGpa);
        when(studentRepository.save(student)).thenReturn(student);
        Student result = studentService.saveStudent(student);
        assertEquals(validGpa, result.getGpa());
        verify(studentRepository).save(student);
    }

//GPA Boundary Test
    @DisplayName("Test gpa is greater then 4.0 or is a negative number")
    @ParameterizedTest(name = "Should throw exception for invalid GPA: {0}")
    @ValueSource(doubles = {5.0, 4.1, -1.0, -0.1}) 
    public void saveStudent_Fail_Parameterized(double invalidGpa) {
        Student student = new Student();
        student.setFirstName("Hacker");
        student.setGpa(invalidGpa);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, 
            () -> studentService.saveStudent(student));        
        assertEquals("Invalid GPA: Must be between 0.0 and 4.0", e.getMessage());
        verify(studentRepository, never()).save(any());
    }
}
