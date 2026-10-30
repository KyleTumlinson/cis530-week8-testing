package edu.bellevue.cis530.week8.repository;

import edu.bellevue.cis530.week8.entity.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentProfileRepository extends JpaRepository<StudentProfile,Long> {
    // Get the student profile through the student ID
    Optional<StudentProfile> findByStudentId(Long id);
}
