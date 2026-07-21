package mn.internhub.demo.service;

import jakarta.persistence.Id;
import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.api.dto.studentApiDto.ResponseComment;
import mn.internhub.demo.api.dto.studentApiDto.ResponseStudentPro;
import mn.internhub.demo.api.dto.studentApiDto.UpdateProfileRequest;
import mn.internhub.demo.data.*;
import mn.internhub.demo.data.enums.ContentTypes;
import mn.internhub.demo.data.enums.EvaluationStatus;
import mn.internhub.demo.data.enums.Role;
import mn.internhub.demo.data.enums.Status;
import mn.internhub.demo.repository.*;
import mn.internhub.demo.service.helperFunctions.gimmeId;
import mn.internhub.demo.service.helperFunctions.isItExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
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
    @Autowired
    private FileEntityRepository fileRepository;
    @Autowired
    private EvaluationRepository evaluationRepository;
    @Autowired
    private gimmeId gimmeId;

    public Student updateProfile(Long userId, UpdateProfileRequest updateRequest) {
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


        studentRepository.save(student);
        return student;
    }
    //сурагчийн мэдээллийг явуулах
    public ResponseStudentPro getProfile(Long userId) {
        isItExist.isStudentByUserId(userId);
        Student student = studentRepository.findByUserId(userId);
        User user = userRepository.getReferenceById(userId);
        return ResponseStudentPro.builder()
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .major(student.getMajor())
                .university(student.getUniversity())
                .courseYear(student.getCourseYear())
                .gpa(student.getGpa())
                .phone(student.getPhone())
                .shortBio(student.getShortBio())
                .skills(student.getSkills())
                .language(student.getLanguages())
                .email(user.getEmail())
                .teacherFirstName(student.getTeacherId() != null ? teacherRepository.getReferenceById(student.getTeacherId()).getFirstName():null)
                .teacherPhone(student.getTeacherId() != null ? teacherRepository.getReferenceById(student.getTeacherId()).getPhone():null)
                .build();
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

    public float getAvgStudentScore(Long userId) {
        isItExist.isStudentByUserId(userId);
        Long studentId = gimmeId.userIdToStudentId(userId);

        List<Evaluation> evaluations = evaluationRepository.findAllByStudentIdAndStatus(studentId, EvaluationStatus.EVALUATED);
        if (evaluations.isEmpty()) {
            return 0.0f;
        }

        Integer evaScore= 0;
        for (Evaluation evaluation : evaluations) {
            evaScore += evaluation.getScore();
        }
        return (float)(evaScore)/(evaluations.size());
    }

    public List<ResponseComment> getStudentComment(Long studentId) {
        isItExist.isStudentExistByStudentId(studentId);
        List<Evaluation> evaluations = evaluationRepository.findAllByStudentIdAndStatus(studentId,EvaluationStatus.EVALUATED);
        List<ResponseComment> responseComment2= evaluations.stream()
                .map(evaluation -> {
                    return ResponseComment.builder()
                            .role(Role.COMPANY)
                            .name(organizationRepository.findById(evaluation.getOrganizationId()).orElseThrow(IllegalAccessError::new).getOrganizationName())
                            .comment(evaluation.getComment())
                            .score(evaluation.getScore())
                            .build();
                        }
                )
                .toList();
        List<ResponseComment> result = new ArrayList<>();
        result.addAll(responseComment2);
        return result;
    }

    public void updateCV(Long userId,MultipartFile file){
        try {
        if (file.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "empty");
        }
        FileEntity fileEntity = fileRepository.findByUserIdAndContentTypes(userId, ContentTypes.CV).orElseThrow(IllegalAccessError::new);
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setFileType(file.getContentType());
        fileEntity.setContentTypes(ContentTypes.CV);
        fileEntity.setData(file.getBytes());
            
        fileRepository.save(fileEntity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
