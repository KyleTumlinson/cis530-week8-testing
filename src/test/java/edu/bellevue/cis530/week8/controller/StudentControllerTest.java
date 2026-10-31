package edu.bellevue.cis530.week8.controller;

import edu.bellevue.cis530.week8.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class StudentControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    StudentService service;

    // create sample json for post request

    // (mockmvc.perform(get("api/students"))
    // responses need verification with stuff like
    // status().isOk(), jsonPath("$.id"), jsonPath("$.firstName")

    // test post /api/students
    // test get /api/students
    // test get /api/students/{id}
    // test delete /api/students/{id}
}
