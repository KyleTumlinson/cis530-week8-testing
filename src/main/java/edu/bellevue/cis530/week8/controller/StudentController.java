package edu.bellevue.cis530.week8.controller;

import edu.bellevue.cis530.week8.entity.Student;
import edu.bellevue.cis530.week8.entity.StudentProfile;
import edu.bellevue.cis530.week8.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Student Operations", description = "Endpoints for managing student records within " +
        "the database.")
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Create a new student
    @Operation(summary = "Create a new student", description = "Creates a new student and saves " +
            "it in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student created successfully",
            content = @Content(schema = @Schema(implementation = StudentProfile.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed", content =  @Content)
    })
    @PostMapping()
    public ResponseEntity<Student> addStudent(@Valid @RequestBody Student student) {
        Student newStudent = studentService.createStudent(student);
        return ResponseEntity.created(URI.create("/api/students/" + newStudent.getId()))
                .body(newStudent);
    }

    // Get all students in database
    @Operation(summary = "Get all students", description = "Retrieves a list of all students stored in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of students",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = StudentProfile.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping()
    public ResponseEntity<List<Student>> findAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // Get specific student by ID
    @Operation(summary = "Get student by ID", description = "Retrieves a single student's details using their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student found successfully",
                    content = @Content(schema = @Schema(implementation = StudentProfile.class))),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Student> findStudentById(@Parameter(description = "The ID of the" +
            " student to retrieve", required = true, example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    // Update a student (must also update the profile)
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id,
                                                 @Valid @RequestBody Student student) {
        return ResponseEntity.ok().body(studentService.updateStudent(id, student));
    }

    // delete student (must also delete profile)
    @Operation(summary = "Delete student by ID",
            description = "Deletes a specific student and their associated profile from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Student deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudentById(@Parameter(description = "The ID of the " +
            "student to delete", required = true, example = "1") @PathVariable Long id) {
        studentService.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }

    // get profile by student id
    @GetMapping("{id}/profile")
    public ResponseEntity<StudentProfile> findProfileByStudentId(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.findProfileByStudentId(id));
    }

    // get students by major
    @GetMapping("/major/{major}")
    public ResponseEntity<List<Student>> findProfileByMajor(@PathVariable String major) {
        return ResponseEntity.ok().body(studentService.getStudentsByMajor(major));
    }

    // get satudents by enrollment year
    @GetMapping("/year/{year}")
    public ResponseEntity<List<Student>> findStudentsByEnrollmentByYear(@PathVariable Integer year) {
        return ResponseEntity.ok().body(studentService.getStudentsByEnrollmentYear(year));
    }
}
