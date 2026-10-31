package edu.bellevue.cis530.week8.service;

import edu.bellevue.cis530.week8.entity.Student;
import edu.bellevue.cis530.week8.entity.StudentProfile;
import edu.bellevue.cis530.week8.exception.ResourceNotFoundException;
import edu.bellevue.cis530.week8.repository.StudentRepository;
import edu.bellevue.cis530.week8.service.impl.StudentService.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private static StudentProfile profile;
    private static Student student;


    @BeforeEach
    void init()
    {
        profile = StudentProfile.builder()
                .id(1L)
                .address("123 main street")
                .city("some city")
                .zip("12345")
                .phone("098-765-4321")
                .emergencyContactName("some emergency contact")
                .emergencyContactPhone("123-456-7890")
                .build();
        student = Student.builder()
                .id(1L)
                .email("test@email.com")
                .enrollmentYear(2020)
                .firstName("test")
                .lastName("student")
                .gpa(3.25f)
                .major("Some Major")
                .profile(profile)
                .build();
    }

    @Test
    void createStudentShouldReturnStudent() {
        when(studentRepository.save(student)).thenReturn(student);

        Student testStudent = studentService.createStudent(student);

        assertNotNull(testStudent);

        assertEquals(student, testStudent);
        assertEquals(student.getId(), testStudent.getId());
        assertEquals(student.getFirstName(), testStudent.getFirstName());
        verify(studentRepository).save(student);
    }

    @Test
    void getAllStudents() {
        List<Student> students = List.of(student);
        when(studentRepository.findAll()).thenReturn(students);

        List<Student> result = studentService.getAllStudents();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("test", result.getFirst().getFirstName());
        assertEquals(1L, result.getFirst().getId());
    }

    @Test
    void getStudentById() {
        when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(student));

        Student result = studentService.getStudentById(1L);

        assertNotNull(result);
        assertEquals(result.getId(), student.getId());
        assertEquals(result.getFirstName(), student.getFirstName());
    }

    @Test
    void getStudentByIdThrowsExceptionWhenStudentDoesntExist()
    {
        Long searchId = 99L;
        when(studentRepository.findById(searchId)).thenThrow(new ResourceNotFoundException("Couldnt find student with id: " + searchId));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            studentService.getStudentById(searchId);
        });

        assertEquals("Couldnt find student with id: 99", exception.getMessage());
    }

    @Test
    void deleteStudentById() {
        studentService.deleteStudentById(1L);

        verify(studentRepository, times(1)).deleteById(1L);

        verifyNoMoreInteractions(studentRepository);
    }
}
