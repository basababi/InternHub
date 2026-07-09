package mn.internhub.demo.service;

import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.api.dto.evaluationApiDto.*;
import mn.internhub.demo.data.*;
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

@Slf4j
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
    @Autowired
    private TeacherRepository teacherRepository;

    //тухайн байгууллаг нь өөр дээр нь дадлга хийсэн сурагчийн үнэлэх
    public Evaluation createEvaluation(Long userId,Long appId, RequestEvaluation request) {
        isItExist.isOrgByUserId(userId);
        //нэвтэрсэн байгуллаг нь хандаж буй application-ий хандах эрх бүхий байгуллаг мөн эсхийг нь шалгах
        Long internshipPostId = applicationRepository.findById(appId).orElseThrow(IllegalAccessError::new).getInternshipPostId();
        boolean thisApplicationIsRelatedToThisOrganization = internshipPostRepository.findById(internshipPostId).orElseThrow(IllegalAccessError::new).getOrganizationId().equals(gimmeId.userIdToOrgId(userId));
        if (!thisApplicationIsRelatedToThisOrganization){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"чинийх биш байшдээээ");
        }
        boolean isAccepted = applicationRepository.findById(appId).orElseThrow(IllegalAccessError::new).getStatus().equals(Status.ACCEPTED);
        if (!isAccepted){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"танай байгууллаг үүнийг эхлээд зөвшөөрөх ёстой");
        }
        //тухайн application-д хариалагдах сурагчийн id-ийг нь авах
        Long studentId =  applicationRepository.findById(appId).orElseThrow(IllegalAccessError::new).getStudentId();
        Evaluation evaluation = Evaluation.builder()
                .organizationId(gimmeId.userIdToOrgId(userId))
                .studentId(studentId)
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
                                Evaluation eva = evaluationRepository.findByStudentId(application.getStudentId());
                                EvaluationStatus evaStatus = (eva != null) ? eva.getStatus() : EvaluationStatus.NOT_EVALUATED;
                                Long evaId = (eva != null) ? eva.getEvaluationId() : null;

                                if (evaStatus == null){
                                    evaStatus = EvaluationStatus.NOT_EVALUATED;
                                }
                                return AccaptedStudents.builder()
                                        .studentId(application.getStudentId())
                                        .firstName(studentRepository.findByUserId(application.getStudentId()).getFirstName())
                                        .lastName(studentRepository.findByUserId(application.getStudentId()).getLastName())
                                        .status(evaStatus)
                                        .evaluationId(evaId)
                                        .build();
                                    }
                            )
                            .toList();
                    return ResponseGetAppEva.builder()
                            .internshipPostId(post.getInternshipPostId())
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
        boolean isOwner = internshipPostRepository.findById(applicationRepository.findById(evaId).orElseThrow(IllegalAccessError::new).getInternshipPostId()).orElseThrow(IllegalAccessError::new).getOrganizationId().equals(gimmeId.userIdToOrgId(userId));
        if (!isOwner){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"чиний эрх чинь хүрэхгүй байна");
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
        if (!evaluationRepository.existsById(evaId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"олсонгүйй");
        }
        boolean isStudent = studentRepository.existsByUserId(userId);
        boolean isTeacher = teacherRepository.existsByUserId(userId);
        if (isStudent){
            //тухайн үнэлгээний сурагчийн id нь нэвтэрсэн сурагчийн id-тай таарахгүй байвал
            if (!evaluationRepository.findById(evaId).orElseThrow(IllegalAccessError::new).getStudentId().equals(studentRepository.findByUserId(userId).getStudentId())){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"чинийх биш байшд");
            }
        }
        else if (isTeacher){
            //хандаж буй үнэлгээний холбогдох сурагчийн холбогдох багшийн id-тай нэвтэрсэн багшийн id-тай таарч байгаа эсхийш шалгаж байна
            Evaluation evaluation = evaluationRepository.findById(evaId).orElseThrow(() -> new IllegalArgumentException("үнэлгээ олдсонгүй"));
            Student student = studentRepository.findById(evaluation.getStudentId()).orElseThrow(() -> new IllegalArgumentException("сурагч олдсонгүй"));
            Teacher teacher = teacherRepository.findByUserId(userId);
            if (student.getTeacherId().equals(teacher.getTeacherId())){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"чи багш нь биш байна");
            }
        }
        return evaluationRepository.findById(evaId).orElseThrow(()->new IllegalArgumentException("үнэлгээ олдсонгүэ"));
    }
    //сурагч нь ерөнхий байдлаар өмнө хийж байсан үнэлгээнүүдээ харна
    public List<ResponseEvaluation> getAllEvaluation(Long userId) {
        isItExist.isStudentByUserId(userId);
        Long studentId = gimmeId.userIdToStudentId(userId);
        //сурагч дээр гарсан бүх үнэлгээг авах
        List<Evaluation> evaluations = evaluationRepository.findAllByStudentId(studentId);
        return evaluations.stream()
                .map(evaluation -> {
                     String orgName = organizationRepository.findById(evaluation.getOrganizationId()).orElseThrow(IllegalAccessError::new).getOrganizationName();
                     InternshipPost post = internshipPostRepository.findById(applicationRepository.findById(evaluation.getApplicationId()).orElseThrow(IllegalAccessError::new).getInternshipPostId()).orElseThrow(IllegalAccessError::new);
                     String postTitle = post.getTitle();
                    return ResponseEvaluation.builder()
                            .evaluationId(evaluation.getEvaluationId())
                            .organizationName(orgName)
                            .InternshipPostTitle(postTitle)
                            .score(evaluation.getScore())
                            .build();
                        }

                )
                .toList();
    }

    public float getAvgScore(Long orgId) {
        isItExist.isOrgByUserId(orgId);
        List<Evaluation> evaluation = evaluationRepository.findAllOrganizationId(orgId);
        Integer allScore = 0;
        for (Evaluation evaluation1 : evaluation) {
            allScore += evaluation1.getScore();
        }
        
        return (float) allScore /evaluation.size();
    }
}

