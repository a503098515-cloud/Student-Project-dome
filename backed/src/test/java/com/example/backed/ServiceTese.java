package com.example.backed;
import com.example.backed.entity.Student;
import com.example.backed.repository.StudentRepository;
import com.example.backed.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class ServiceTest {

    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentService studentService;

//test gpa has save
  @Test
    void saveStudent_Success() {
        Student student = createStudent("John", 3.5);
        when(studentRepository.save(student)).thenReturn(student);
        Student result = studentService.saveStudent(student);

        assertEquals(3.5, result.getGpa());
        verify(studentRepository).save(student); 
    }


    //Test gpa is more then 4.0
    @Test
    void saveStudent_Fail_GpaTooHigh() {
        Student student = createStudent("Superman", 5.0);

        assertThrows(RuntimeException.class, () -> studentService.saveStudent(student));
        verify(studentRepository, never()).save(any());
    }

//Test whether the GPA is a negative number
    @Test
    void saveStudent_Fail_GpaNegative() {
        Student student = createStudent("Hacker", -1.5);
        Exception e = assertThrows(RuntimeException.class, () -> studentService.saveStudent(student));
        
        // Verify the error message
        assertEquals("Invalid GPA: GPA must be between 0 and 4.0", e.getMessage());
        verify(studentRepository, never()).save(any());
    }

    // Private methods
    private Student createStudent(String name, double gpa) {
        Student s = new Student();
        s.setFirstName(name);
        s.setGpa(gpa);
        return s;
    }
}
