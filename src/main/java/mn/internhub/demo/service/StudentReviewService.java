package mn.internhub.demo.service;

import mn.internhub.demo.api.dto.studentReviewApiDto.ResponseStudentReview;
import mn.internhub.demo.api.dto.studentReviewApiDto.requestStudentReview;
import mn.internhub.demo.data.Student;
import mn.internhub.demo.data.StudentReview;
import mn.internhub.demo.data.User;
import mn.internhub.demo.data.enums.Role;
import mn.internhub.demo.repository.*;
import mn.internhub.demo.service.helperFunctions.isItExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import java.time.LocalDate;
import java.util.List;

@Service
public class StudentReviewService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentReviewRepository studentReviewRepository;
    @Autowired
    private isItExist isItExist;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    public StudentReview postStudentReview(Long userId, Long studentId, requestStudentReview request) {
        isItExist.isStudentExistByStudentId(studentId);
        StudentReview studentReview = StudentReview.builder()
                .studentId(studentId)
                .userId(userId)
                .rate(request.rate())
                .Comment(request.Comment())
                .createdAt(LocalDateTime.now())
                .build();
        studentReviewRepository.save(studentReview);
        return studentReview;
    }
    //тухайн нэг сурагч дээр ирсэн сэтгэгдэлийг авах
    public List<ResponseStudentReview> getStudentReviews(Long userId, Long studentId) {
        isItExist.isStudentExistByStudentId(studentId);
        List<StudentReview> studentReviews = studentReviewRepository.findAllByStudentId(studentId);
        return studentReviews.stream().map(studentReview ->
                {
                    DateTimeFormatter formatter =
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


                    User user = userRepository.getReferenceById(studentReview.getUserId());
                    String name = "";
                    if (user.getRole() == Role.STUDENT){
                        name = studentRepository.findByUserId(studentReview.getUserId()).getFirstName();
                    } else if (user.getRole() == Role.COMPANY) {
                        name = organizationRepository.findByUserId(studentReview.getUserId()).getOrganizationName();
                    } else if (user.getRole() == Role.TEACHER) {
                        name = teacherRepository.findByUserId(studentReview.getUserId()).getFirstName();
                    }
                    return ResponseStudentReview.builder()
                            .name(name)
                            .rate(studentReview.getRate())
                            .Comment(studentReview.getComment())
                            .createdAt(studentReview.getCreatedAt().format(formatter))
                            .build();
                })
                .toList();

    }
    //тухайн нэг сурагч дээр сэтгэгдэлийн голчыг гаргах
    public double getAvgScore(Long studentId) {
        List<StudentReview> studentReviews = studentReviewRepository.findAllByStudentId(studentId);
        double sum = studentReviews.stream()
                .mapToDouble(StudentReview::getRate)
                .sum();
        return sum/studentReviews.size();
    }
}
