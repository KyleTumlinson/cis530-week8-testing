package service;

import edu.bellevue.cis530.week8.entity.Student;
import edu.bellevue.cis530.week8.entity.StudentProfile;
import edu.bellevue.cis530.week8.repository.StudentRepository;
import edu.bellevue.cis530.week8.service.StudentService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private static StudentProfile profile;
    private static Student student;


    @BeforeEach
    static void init()
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
    void createStudent() {
    }

    @Test
    void getAllStudents() {
    }

    @Test
    void getStudentById() {
    }

    @Test
    void deleteStudentById() {
    }

    // Test createStudent

    // Test GetAllStudents

    // Test getStudentById

    // Test deleteStudent
}
