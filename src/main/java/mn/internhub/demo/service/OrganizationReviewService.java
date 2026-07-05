package mn.internhub.demo.service;

import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.api.OrganizationReviewApi;
import mn.internhub.demo.api.dto.OrganizationReviewApi.RequestPostReview;
import mn.internhub.demo.api.dto.OrganizationReviewApi.RequestUpdateReview;
import mn.internhub.demo.data.OrganizationReview;
import mn.internhub.demo.data.Organizations;
import mn.internhub.demo.data.Student;
import mn.internhub.demo.data.enums.UserStatus;
import mn.internhub.demo.repository.OrganizationRepository;
import mn.internhub.demo.repository.OrganizationReviewRepository;
import mn.internhub.demo.repository.StudentRepository;
import mn.internhub.demo.repository.UserRepository;
import mn.internhub.demo.service.helperFunctions.gimmeId;
import mn.internhub.demo.service.helperFunctions.isItExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Slf4j
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
    @Autowired
    private UserRepository userRepository;

    //Сурагч нь байгууллагаа үнэлэх
    public OrganizationReview createReview(Long userId,Long orgId ,RequestPostReview request) {
        isItExist.isStudentByUserId(userId);
        isItExist.isOrgExistByOrgId(orgId);
        Long studentId = gimmeId.userIdToStudentId(userId);
        long sequence = anonymousService.getSequence();
        Student student = studentRepository.findById(studentId).orElseThrow(IllegalAccessError::new);

        OrganizationReview review = OrganizationReview.builder()
                .organizationId(orgId)
                .studentName(request.isAnonymous()?"Anonymous"+sequence:student.getFirstName()+" "+student.getLastName())
                .studentId(studentId)
                .rating(request.rating())
                .comment(request.comment())
                .isAnonymous(request.isAnonymous())
                .createdAt(LocalDate.now())
                .build();
        organizationReviewRepository.save(review);
        log.info("энэ хүртэл ирлээ 6");
        return review;
    }
    //тухайн байгууллаг дээр ирсэн бүх үнэлгээг сэтгэгдэл олон нийт харах
    public List<OrganizationReview> getAllReview(Long orgId) {
        isItExist.isOrgExistByOrgId(orgId);
        return organizationReviewRepository.findAllByOrganizationId(orgId);
    }
    //сурагч нь оруулсан үнэлгээгээ засах
    public OrganizationReview updateReview(Long userId, Long revId, RequestUpdateReview request) {
        isItExist.isStudentByUserId(userId);
        boolean isBanned = userRepository.findById(userId).orElseThrow(IllegalAccessError::new).getStatus().equals(UserStatus.BANNED);
        if (isBanned){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"ээ чи бандуулцан байна шдэээ");
        }
        if (!organizationReviewRepository.existsById(revId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"олдсонгүй");
        }
        Long studentId = gimmeId.userIdToStudentId(userId);
        OrganizationReview review = organizationReviewRepository.findById(revId).orElseThrow(IllegalAccessError::new);
        if (review.getOrganizationReviewId().equals(studentId)){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"хэрэглэгчийн эрх хүрэхгүй байна");
        }
        long sequence = anonymousService.getSequence();
        Student student = studentRepository.findById(studentId).orElseThrow(IllegalAccessError::new);

        if (request.isAnonymous()){
            log.info("aaaaa22");
            review.setAnonymous(true);
            review.setStudentName("Anonymous"+sequence);
        }
        else if(!request.isAnonymous()) {
            log.info("aaaaa21");
            review.setAnonymous(false);
            review.setStudentName(student.getFirstName()+" "+student.getLastName());
        }
        if (request.rating() != null){
            log.info("aaaaa1");
            review.setRating(request.rating());
        }
        if (request.comment() != null){
            log.info("aaaaa3");
            review.setComment(request.comment());
        }
        organizationReviewRepository.save(review);
        return review;
    }
    //сурагч нь үнэлсэн үнэлгээгээ устгах
    public void deleteReview(Long userId, Long revId) {
        isItExist.isStudentByUserId(userId);
        boolean isExistReview = organizationReviewRepository.existsById(revId);
        if (!isExistReview){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"олдсонгүй");
        }
        Long studentId = gimmeId.userIdToStudentId(userId);
        OrganizationReview review = organizationReviewRepository.findById(revId).orElseThrow(IllegalAccessError::new);
        if (review.getOrganizationReviewId().equals(studentId)){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"хэрэглэгчийн эрх хүрэхгүй байна");
        }
        organizationReviewRepository.deleteById(revId);
    }
}
