package edu.bellevue.cis530.week8.service;

import edu.bellevue.cis530.week8.entity.Student;
import edu.bellevue.cis530.week8.entity.StudentProfile;

import java.util.List;

public interface StudentService {
    Student createStudent(Student student);
    List<Student> getAllStudents();
    List<Student> getStudentsByMajor(String major);
    List<Student> getStudentsByEnrollmentYear(int enrollmentYear);
    Student getStudentById(Long id);
    Student updateStudent(Long id, Student student);
    void deleteStudentById(Long id);
    StudentProfile findProfileByStudentId(Long id);
}
