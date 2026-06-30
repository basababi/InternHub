package mn.internhub.demo.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.api.ApplicationApi;
import mn.internhub.demo.api.dto.RequestApplication;
import mn.internhub.demo.api.dto.ResponseApplicationDetail;
import mn.internhub.demo.data.Application;
import mn.internhub.demo.data.Student;
import mn.internhub.demo.data.enums.PaymentStatus;
import mn.internhub.demo.data.enums.Status;
import mn.internhub.demo.repository.ApplicationRepository;
import mn.internhub.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Slf4j
@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private StudentRepository studentRepository;

    //application хүсэлт үүсгэнэ post
    public Application createApplication(RequestApplication request) {
        Application application = Application.builder()
                .studentId(request.studentId())
                .internshipPostId(request.internshipPostId())
                .status(Status.PENDING)
                .coverLetter(request.coverLetter())
                .paymentStatus(PaymentStatus.SUCCESS)
                .submittedAt(LocalDateTime.now())
                .build();
        return applicationRepository.save(application);
    }
    // багш эсвэл комнаны нь тухайн application-ийг илүү дэлгэрэнгүй харна үүнд нв сурагчийн дэлгэрэнгүй мэдээлэл орно.
    public ResponseApplicationDetail getApplicationDetail(Long applicationId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found application"));
        Student student = studentRepository.findByUserId(application.getStudentId());
        ResponseApplicationDetail responseApplicationDetail = ResponseApplicationDetail.builder()
                .firstName(student.getFirstName())
                .lastName(student.getFirstName())
                .major(student.getMajor())
                .university(student.getUniversity())
                .courseYear(student.getCourseYear())
                .gpa(student.getGpa())
                .phone(student.getPhone())
                .shortBio(student.getShortBio())
                .skills(student.getSkills())
                .languages(student.getLanguages())
                .coverLetter(application.getCoverLetter())
                .createdAt(application.getSubmittedAt())
                .build();
        return responseApplicationDetail;
    }
    //application id-гаар нь тухайн application-ий status-ийг өөрчилнө
    public void updateApplicationStatus(Long id, Status status) {
        Application application = applicationRepository.getById(id);
        application.setStatus(status);
        applicationRepository.save(application);
        log.info("амжилттай болсон байх магадлалтай: {}",application.getStatus());
    }
    //applicaiton-аа сурагч нь өөрөө татгалзах
    public void updateApplicationStatusByStudent(Long id) {
        Application application = applicationRepository.getById(id);
        application.setStatus(Status.WITHDRAWN);
        applicationRepository.save(application);
        log.info("сурагч нь ажилттай өөрчилсөн байх магадлалтай: {}",application.getStatus());
    }
}
