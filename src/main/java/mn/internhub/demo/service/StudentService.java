package mn.internhub.demo.service;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.api.dto.UpdateProfileRequest;
import mn.internhub.demo.data.Application;
import mn.internhub.demo.data.Student;
import mn.internhub.demo.data.User;
import mn.internhub.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Slf4j
@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private ApplicationRepository applicationRepository;

    public Student updateProfile( Long id,UpdateProfileRequest updateRequest) {
        boolean userExist = userRepository.existsById(id);
        if (!userExist) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user doesn't found");
        }
        log.info("хэрэглэгчийн id байна id нь :{}",id);
        Student student = studentRepository.findByUserId(id);
        log.info("тухайн id-д хариалагдах сурагчийн мэдээллийг авсан");
        if(updateRequest.firstName() != null){
            student.setFirstName(updateRequest.firstName());
        }
        if (updateRequest.lastName() != null){
            student.setLastName(updateRequest.lastName());
        }
        if (updateRequest.major() != null) {
            student.setMajor(updateRequest.major());
        }
        if (updateRequest.university() != null) {
            student.setUniversity(updateRequest.university());
        }
        if (updateRequest.courseYear() != null){
            student.setCourseYear(updateRequest.courseYear());
        }
        if (updateRequest.gpa() != null){
            student.setGpa(updateRequest.gpa());
        }
        if (updateRequest.phone() != null){
            student.setPhone(updateRequest.phone());
        }
        if (updateRequest.shortBio() != null){
            student.setShortBio(updateRequest.shortBio());
        }
        if (updateRequest.skills() != null){
            student.setSkills(updateRequest.skills());
        }
        if (updateRequest.languages() != null){
            student.setLanguages(updateRequest.languages());
        }
        studentRepository.save(student);
        log.info("сурагчийн мэдээллийг save хийсэн");
        return student;
    }
    //сурагчийн мэдээллийг явуулах
    public Student getProfile(Long userId) {
        return studentRepository.findByUserId(userId);
    }

    public Student getStudentProfile(Long userId, Long id) {
        boolean isTeacher = teacherRepository.existsByUserId(userId);
        boolean isOrganization = organizationRepository.existsByUserId(userId);
        if(!isTeacher && !isOrganization){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Access denied");
        }
        boolean existStudent = studentRepository.existsById(id);
        if(!existStudent) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user doesn't found");
        }
        log.info("Сурагчийн мэдээллийг явуулсан");
        return studentRepository.findById(id).orElseThrow(IllegalStateException::new);
    }
    //тухайн сурагчийн илгээсэн бүх ажлийн хүсэлтийг харуулна
    public List<Application> getApplications(User user){
        Long studentId = studentRepository.findByUserId(user.getUserId()).getStudentId();
        List<Application> applications = applicationRepository.findByStudentId(studentId);
        return applications;
    }

}
