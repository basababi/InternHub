package mn.internhub.demo.service;

import mn.internhub.demo.api.OrganizationReviewApi;
import mn.internhub.demo.api.dto.OrganizationReviewApi.RequestPostReview;
import mn.internhub.demo.data.OrganizationReview;
import mn.internhub.demo.data.Organizations;
import mn.internhub.demo.data.Student;
import mn.internhub.demo.repository.OrganizationRepository;
import mn.internhub.demo.repository.OrganizationReviewRepository;
import mn.internhub.demo.repository.StudentRepository;
import mn.internhub.demo.service.helperFunctions.gimmeId;
import mn.internhub.demo.service.helperFunctions.isItExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrganizationReviewService {
    @Autowired
    private OrganizationReviewRepository organizationReviewRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private isItExist isItExist;
    @Autowired
    private gimmeId gimmeId;
    @Autowired
    private AnonymousService anonymousService;
    //Сурагч нь байгууллагаа үнэлэх
    public OrganizationReview createReview(Long userId,Long orgId ,RequestPostReview request) {
        isItExist.isStudentByUserId(userId);
        isItExist.isOrgExistByOrgId(orgId);
        Long studentId = gimmeId.userIdToStudentId(userId);
        long sequence = anonymousService.getSequence();
        Student student = studentRepository.findById(studentId).orElseThrow(IllegalAccessError::new);

        OrganizationReview review = OrganizationReview.builder()
                .organizationId(orgId)
                .studentName(request.isanonymous()?student.getFirstName()+student.getLastName():"Anonymous"+sequence)
                .studentId(studentId)
                .rating(request.rating())
                .comment(request.comment())
                .isAnonymous(request.isanonymous())
                .createdAt(LocalDate.now())
                .build();
        organizationReviewRepository.save(review);
        return review;
    }
}
