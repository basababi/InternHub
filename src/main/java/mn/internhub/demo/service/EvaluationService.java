package mn.internhub.demo.service;

import mn.internhub.demo.api.dto.evaluationApiDto.RequestEvaluation;
import mn.internhub.demo.data.Application;
import mn.internhub.demo.data.Evaluation;
import mn.internhub.demo.repository.*;
import mn.internhub.demo.service.helperFunctions.gimmeId;
import mn.internhub.demo.service.helperFunctions.isItExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class EvaluationService {
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private EvaluationRepository evaluationRepository;
    @Autowired
    private isItExist isItExist;
    @Autowired
    private gimmeId gimmeId;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private InternshipPostRepository internshipPostRepository;

    //тухайн байгууллаг нь өөр дээр нь дадлга хийсэн сурагчийн үнэлэх
    public Evaluation createEvaluation(Long userId, RequestEvaluation request) {
        isItExist.isOrgByUserId(userId);
        Long internshipPostId = applicationRepository.findById(request.applicationId()).orElseThrow(IllegalAccessError::new).getInternshipPostId();
        boolean thisApplicationIsRelatedToThisOrganization = internshipPostRepository.findById(internshipPostId).orElseThrow(IllegalAccessError::new).getOrganizationId().equals(gimmeId.userIdToOrgId(userId));
        if (!thisApplicationIsRelatedToThisOrganization){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"чинийх биш байшдээээ");
        }
        Evaluation evaluation = Evaluation.builder()
                .organizationId(gimmeId.userIdToOrgId(userId))
                .studentId(request.studentId())
                .applicationId(request.applicationId())
                .score(request.score())
                .comment(request.comment())
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .build();
        evaluationRepository.save(evaluation);
        return evaluation;
    }

}
