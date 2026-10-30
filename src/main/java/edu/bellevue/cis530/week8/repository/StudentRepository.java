package edu.bellevue.cis530.week8.repository;

import edu.bellevue.cis530.week8.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    // find all students with the given major
    List<Student> findByMajor(String major);
    // find all students based on the given enrollment year
    List<Student> findByEnrollmentYear(int enrollmentYear);
}
