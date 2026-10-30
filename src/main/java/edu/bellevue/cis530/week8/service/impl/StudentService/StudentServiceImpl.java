package edu.bellevue.cis530.week8.service.impl.StudentService;

import edu.bellevue.cis530.week8.entity.Student;
import edu.bellevue.cis530.week8.entity.StudentProfile;
import edu.bellevue.cis530.week8.exception.ResourceNotFoundException;
import edu.bellevue.cis530.week8.repository.StudentProfileRepository;
import edu.bellevue.cis530.week8.repository.StudentRepository;
import edu.bellevue.cis530.week8.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentProfileRepository studentProfileRepository;

    public StudentServiceImpl(StudentRepository studentRepository, StudentProfileRepository studentProfileRepository) {
        this.studentRepository = studentRepository;
        this.studentProfileRepository = studentProfileRepository;
    }

    // create a new student implementation
    @Override
    @Transactional
    public Student createStudent(Student student) {


        return studentRepository.save(student);
    }

    // get all students implementation
    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // get all students of given major implementation
    @Override
    public List<Student> getStudentsByMajor(String major) {
        return studentRepository.findByMajor(major);
    }

    // get all students that enrolled in the given year
    @Override
    public List<Student> getStudentsByEnrollmentYear(int enrollmentYear) {
        return studentRepository.findByEnrollmentYear(enrollmentYear);
    }

    // get student by id or throw an exception
    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Unable to " +
                "find student with id: " + id));
    }

    @Override
    @Transactional
    public Student updateStudent(Long id, Student student) {
        // update the student fields (other than the profile)
        Student oldStudent = getStudentById(id);
        oldStudent.setFirstName(student.getFirstName());
        oldStudent.setLastName(student.getLastName());
        oldStudent.setEmail(student.getEmail());
        oldStudent.setMajor(student.getMajor());
        oldStudent.setGpa(student.getGpa());
        oldStudent.setEnrollmentYear(student.getEnrollmentYear());

        // get the incoming students profile
        StudentProfile incomingProfile = student.getProfile();
        // if the profile is not null
        if (incomingProfile != null) {
            // get the profile that should be updated
            StudentProfile existingProfile = oldStudent.getProfile();
            // if the profile that needs to be updated exists in the Student update its fields
            if(existingProfile != null) {
                existingProfile.setAddress(incomingProfile.getAddress());
                existingProfile.setCity(incomingProfile.getCity());
                existingProfile.setState(incomingProfile.getState());
                existingProfile.setZip(incomingProfile.getZip());
                existingProfile.setPhone(incomingProfile.getPhone());
                existingProfile.setEmergencyContactName(incomingProfile.getEmergencyContactName());
                existingProfile.setEmergencyContactPhone(incomingProfile.getEmergencyContactPhone());

                existingProfile.setStudent(oldStudent);
            // if the profile doesnt exist already
            }else {
                incomingProfile.setStudent(oldStudent);
                oldStudent.setProfile(incomingProfile);
            }
        // set old student profile to null if the profile doesn't exist (will throw an exception)
        } else{
            oldStudent.setProfile(null);
        }
        return studentRepository.save(oldStudent);
    }

    @Override
    @Transactional
    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public StudentProfile findProfileByStudentId(Long id) {
        return studentProfileRepository.findByStudentId(id).orElseThrow(() -> new ResourceNotFoundException("Unable to find a Student Profile associated with student id: " + id));
    }
}
