package edu.bellevue.cis530.week8.controller;

import edu.bellevue.cis530.week8.entity.Student;
import edu.bellevue.cis530.week8.entity.StudentProfile;
import edu.bellevue.cis530.week8.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Create a new student
    @PostMapping()
    public ResponseEntity<Student> addStudent(@Valid @RequestBody Student student) {
        Student newStudent = studentService.createStudent(student);
        return ResponseEntity.created(URI.create("/api/students/" + newStudent.getId()))
                .body(newStudent);
    }

    // Get all students in database
    @GetMapping()
    public ResponseEntity<List<Student>> findAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // Get specific student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> findStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    // Update a student (must also update the profile)
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id,
                                                 @Valid @RequestBody Student student) {
        return ResponseEntity.ok().body(studentService.updateStudent(id, student));
    }

    // delete student (must also delete profile)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudentById(@PathVariable Long id) {
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
