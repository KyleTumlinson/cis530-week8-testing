package edu.bellevue.cis530.week8.repository;

import edu.bellevue.cis530.week8.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// default JPA repository for member
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // Add findByUsername as na option for the repository
    Optional<Member> findByUsername(String username);
}
