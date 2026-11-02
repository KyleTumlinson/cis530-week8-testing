package edu.bellevue.cis530.week8.controller;

import edu.bellevue.cis530.week8.entity.Student;
import edu.bellevue.cis530.week8.entity.StudentProfile;
import edu.bellevue.cis530.week8.exception.ResourceNotFoundException;
import edu.bellevue.cis530.week8.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class StudentControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    StudentService service;

    // test post /api/students
    @Test
    void postStudentShouldCreateStudent() throws Exception
    {
        String jsonInput =
                """
                {
                  "firstName": "Sara",
                  "lastName": "Feilmann",
                  "email": "sara.feilmann@example.com",
                  "major": "Computer Science",
                  "gpa": 3.75,
                  "enrollmentYear": 2024,
                  "profile": {
                    "phone": "402-555-1212",
                    "address": "123 Main St",
                    "city": "Omaha",
                    "state": "NE",
                    "zip": "68102",
                    "emergencyContactName": "John Feilmann",
                    "emergencyContactPhone": "402-555-7777"
                  }
                }
            """;

        Student mockStudent = Student.builder()
                .id(1L)
                .firstName("Sara")
                .lastName("Feilmann")
                .email("sara.feilmann@example.com")
                .major("Computer Science")
                .gpa(3.75f)
                .enrollmentYear(2024)
                .profile(
                        StudentProfile.builder()
                                .id(1L)
                                .phone("402-555-1212")
                                .address("123 Main St")
                                .city("Omaha")
                                .state("NE")
                                .zip("68102")
                                .emergencyContactName("John Feilmann")
                                .emergencyContactPhone("402-555-7777")
                                .build()
                ).build();

        when(service.createStudent(any(Student.class))).thenReturn(mockStudent);

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isCreated()) // Verifies 201 Created
                .andExpect(header().string("Location", "/api/students/1"))
                .andExpect(jsonPath("$.id").value(1)) // Guidelines requirement
                .andExpect(jsonPath("$.firstName").value("Sara"))
                .andExpect(jsonPath("$.profile.id").value(1));
    }

    // test get /api/students
    @Test
    void findAllShouldReturnListOfStudents() throws Exception {
        Student mockStudent = Student.builder()
                .id(1L)
                .firstName("Sara")
                .lastName("Feilmann")
                .email("sara.feilmann@example.com")
                .major("Computer Science")
                .gpa(3.75f)
                .enrollmentYear(2024)
                .profile(
                        StudentProfile.builder()
                                .id(1L)
                                .phone("402-555-1212")
                                .address("123 Main St")
                                .city("Omaha")
                                .state("NE")
                                .zip("68102")
                                .emergencyContactName("John Feilmann")
                                .emergencyContactPhone("402-555-7777")
                                .build()
                )
                .build();

        when(service.getAllStudents()).thenReturn(List.of(mockStudent));

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Sara"))
                .andExpect(jsonPath("$[0].profile.id").value(1));
    }

    // test get /api/students/{id}
    @Test
    void findByIdShouldReturnStudent() throws Exception {
        Student mockStudent = Student.builder()
                .id(1L)
                .firstName("Sara")
                .lastName("Feilmann")
                .email("sara.feilmann@example.com")
                .major("Computer Science")
                .gpa(3.75f)
                .enrollmentYear(2024)
                .profile(
                        StudentProfile.builder()
                                .id(1L)
                                .phone("402-555-1212")
                                .address("123 Main St")
                                .city("Omaha")
                                .state("NE")
                                .zip("68102")
                                .emergencyContactName("John Feilmann")
                                .emergencyContactPhone("402-555-7777")
                                .build()
                )
                .build();

        when(service.getStudentById(1L)).thenReturn(mockStudent);

        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Sara"))
                .andExpect(jsonPath("$.profile.id").value(1));
    }

    @Test
    void findByIdShouldThrowExceptionWhenStudentNotFound() throws ResourceNotFoundException {
        when(service.getStudentById(1L)).thenThrow(new ResourceNotFoundException("Student not found"));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> service.getStudentById(1L));

        assertEquals("Student not found", exception.getMessage());
    }

    // test delete /api/students/{id}

    @Test
    void deleteShouldDeleteStudent() throws Exception {
        mockMvc.perform(delete("/api/students/1"))
                .andExpect(status().isNoContent());

        verify(service).deleteStudentById(1L);
    }
}
