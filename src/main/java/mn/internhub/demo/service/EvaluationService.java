package mn.internhub.demo.service;

import mn.internhub.demo.api.dto.evaluationApiDto.*;
import mn.internhub.demo.data.Application;
import mn.internhub.demo.data.Evaluation;
import mn.internhub.demo.data.InternshipPost;
import mn.internhub.demo.data.User;
import mn.internhub.demo.data.enums.EvaluationStatus;
import mn.internhub.demo.data.enums.Status;
import mn.internhub.demo.repository.*;
import mn.internhub.demo.service.helperFunctions.gimmeId;
import mn.internhub.demo.service.helperFunctions.isItExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

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
    public Evaluation createEvaluation(Long userId,Long appId, RequestEvaluation request) {
        isItExist.isOrgByUserId(userId);
        Long internshipPostId = applicationRepository.findById(appId).orElseThrow(IllegalAccessError::new).getInternshipPostId();
        boolean thisApplicationIsRelatedToThisOrganization = internshipPostRepository.findById(internshipPostId).orElseThrow(IllegalAccessError::new).getOrganizationId().equals(gimmeId.userIdToOrgId(userId));
        if (!thisApplicationIsRelatedToThisOrganization){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"чинийх биш байшдээээ");
        }
        Evaluation evaluation = Evaluation.builder()
                .organizationId(gimmeId.userIdToOrgId(userId))
                .studentId(request.studentId())
                .applicationId(appId)
                .score(request.score())
                .comment(request.comment())
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .status(EvaluationStatus.EVALUATED)
                .build();
        evaluationRepository.save(evaluation);
        return evaluation;
    }
    //тухайн байгууллаг нь нийт үнэлгээ хийх зүйлсээ харнаа
    public List<ResponseGetAppEva> getAllAppEva(Long userId) {
        isItExist.isOrgByUserId(userId);
        Long orgId = gimmeId.userIdToOrgId(userId);
        List<InternshipPost> posts = internshipPostRepository.findAllByOrganizationId(orgId);
        return posts.stream()
                .map(post -> {
                    List<Application> applications = applicationRepository.findAllByInternshipPostIdAndStatus(post.getInternshipPostId(),Status.ACCEPTED);
                    List<AccaptedStudents> students = applications.stream()
                            .map(application -> {
                                return AccaptedStudents.builder()
                                        .studentId(application.getStudentId())
                                        .firstName(studentRepository.findByUserId(application.getStudentId()).getFirstName())
                                        .lastName(studentRepository.findByUserId(application.getStudentId()).getLastName())
                                        .status(evaluationRepository.findByStudentId(application.getStudentId()).getStatus())
                                        .evaluationId(evaluationRepository.findByStudentId(application.getStudentId()).getEvaluationId())
                                        .build();
                                    }
                            )
                            .toList();
                    return ResponseGetAppEva.builder()
                            .applicationId(post.getInternshipPostId())
                            .title(post.getTitle())
                            .vacancyCount(post.getVacancyCount())
                            .students(students)
                            .build();
                })
                .toList();
    }
    //тухайн байгуллаг нь өөрчдийн үнэлгээгээ өөрчлөх
    public Evaluation updateEvaluation(Long userId,long evaId ,updateEvaluation request) {
        isItExist.isOrgByUserId(userId);
        boolean isEvaExist = evaluationRepository.existsById(evaId);
        if (!isEvaExist){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND,"өөрчлөх гээд байгаа үнэлгээ чинь байхгүй байншдэ");
        }
        Evaluation evaluation = evaluationRepository.findById(evaId).orElseThrow(IllegalAccessError::new);
        if (request.comment() != null){
            evaluation.setComment(request.comment());
        }
        if (request.score() != null){
            evaluation.setScore(request.score());
        }
        evaluation.setUpdatedAt(LocalDate.now());
        return evaluationRepository.save(evaluation);
    }
    //тухайн сурагчийн нүлгээг сурагч болон багш нь харах
    public Evaluation getEvaluationById(Long userId, Long evaId) {
        isItExist.isStudentByUserId(userId);
        isItExist.isTeacherExistByTeacherId(userId);
        return evaluationRepository.findById(evaId).orElseThrow(IllegalAccessError::new);
    }
    //сурагч нь ерөнхий байдлаар өмнө хийж байсан үнэлгээнүүдээ харна
    public List<ResponseEvaluation> getAllEvaluation(Long userId) {
        isItExist.isStudentByUserId(userId);
        Long studentId = gimmeId.userIdToStudentId(userId);
        List<Evaluation> evaluations = evaluationRepository.findAllByStudentId(studentId);
        return evaluations.stream()
                .map(evaluation -> {
                    String orgName = organizationRepository.findById(evaluation.getOrganizationId()).orElseThrow(IllegalAccessError::new).getOrganizationName();
                    String internshipPostName = internshipPostRepository.findById(applicationRepository.findByInternshipPostId(evaluation.getEvaluationId()).getInternshipPostId()).orElseThrow(IllegalAccessError::new).getTitle();
                    return ResponseEvaluation.builder()
                            .evaluationId(evaluation.getEvaluationId())
                            .organizationName(orgName)
                            .InternshipPostTitle(internshipPostName)
                            .score(evaluation.getScore())
                            .build();
                        }

                )
                .toList();
    }
}
