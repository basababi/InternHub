package mn.internhub.demo.service;

import mn.internhub.demo.api.dto.studentReviewApiDto.requestStudentReview;
import mn.internhub.demo.data.Student;
import mn.internhub.demo.data.StudentReview;
import mn.internhub.demo.data.User;
import mn.internhub.demo.repository.StudentRepository;
import mn.internhub.demo.repository.StudentReviewRepository;
import mn.internhub.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentReviewService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentReviewRepository studentReviewRepository;

    public StudentReview postStudentReview(Long userId, Long studentId, requestStudentReview request) {
        return null;
    }
}
