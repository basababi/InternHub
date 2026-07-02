package mn.internhub.demo.service;

import mn.internhub.demo.data.Student;
import mn.internhub.demo.repository.*;
import mn.internhub.demo.service.helperFunctions.isItExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private OrganizationReviewRepository organizationReviewRepository;
    @Autowired
    private EvaluationRepository evaluationRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private isItExist isItExist;
    ////систесийн бүх сурагчийн авна
    public List<Student> getAllStudent(Long userId) {
        isItExist.isAdminByUserId(userId);
        return studentRepository.findAll();
    }
    //сурагчийн дэлгэрэнгүй мэдээллйиг авна
    public Student getAllStudentDetailById(Long userId, Long studentId) {
        isItExist.isAdminByUserId(userId);
        isItExist.isStudentByUserId(studentId);
        return studentRepository.findById(studentId).orElseThrow(IllegalAccessError::new);
    }
}
