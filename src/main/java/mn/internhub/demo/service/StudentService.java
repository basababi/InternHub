package mn.internhub.demo.service;

import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.api.dto.studentApiDto.UpdateProfileRequest;
import mn.internhub.demo.data.Application;
import mn.internhub.demo.data.Student;
import mn.internhub.demo.data.User;
import mn.internhub.demo.repository.*;
import mn.internhub.demo.service.helperFunctions.isItExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
    @Autowired
    private isItExist isItExist;
    @Autowired
    private FileEntityService fileEntityService;

    public Student updateProfile(Long userId, UpdateProfileRequest updateRequest, MultipartFile file) {
        boolean userExist = userRepository.existsById(userId);
        if (!userExist) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user doesn't found");
        }
        Student student = studentRepository.findByUserId(userId);
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
        if (updateRequest.teacherId() != null){
            student.setTeacherId(updateRequest.teacherId());
        }

        if (!fileEntityService.updateCv(userId,file)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"файл байршуулхад алдаа гарлаа");
        }
        studentRepository.save(student);
        return student;
    }
    //сурагчийн мэдээллийг явуулах
    public Student getProfile(Long userId) {
        return studentRepository.findByUserId(userId);
    }

    //сурагч нь өөрийн мэдээлэлээ авна
    public Student getStudentProfile(Long studentId){
        isItExist.isStudentExistByStudentId(studentId);
        return studentRepository.findById(studentId).orElseThrow(IllegalStateException::new);
    }
    //тухайн сурагчийн илгээсэн бүх ажлийн хүсэлтийг харуулна
    public List<Application> getApplications(User user){
        Long studentId = studentRepository.findByUserId(user.getUserId()).getStudentId();
        return applicationRepository.findAllByStudentId(studentId);
    }
    //тухайн сурагч нь тодорхой нэг хүсэлтийг дэлгэрэнгүй харах
    public Application getApplicationDetail(Long applicationId) {
        return applicationRepository.findById(applicationId).orElseThrow(IllegalStateException::new);
    }

    //Бүртгэлтэй буй нийт сурагчийн тоо
    public long getAllStudentNum() {
        return studentRepository.count();
    }
}
