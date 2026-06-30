package mn.internhub.demo.service;

import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.api.dto.applicationApiDto.RequestApplication;
import mn.internhub.demo.api.dto.applicationApiDto.ResponseApplication;
import mn.internhub.demo.api.dto.applicationApiDto.ResponseApplicationDetail;
import mn.internhub.demo.api.dto.applicationApiDto.ResponseApplicationsToOrganization;
import mn.internhub.demo.data.Application;
import mn.internhub.demo.data.InternshipPost;
import mn.internhub.demo.data.Student;
import mn.internhub.demo.data.enums.PaymentStatus;
import mn.internhub.demo.data.enums.Status;
import mn.internhub.demo.repository.ApplicationRepository;
import mn.internhub.demo.repository.InternshipPostRepository;
import mn.internhub.demo.repository.OrganizationRepository;
import mn.internhub.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private InternshipPostRepository internshipPostRepository;


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
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found application"));
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
        isApplicationExist(id);
        Application application = applicationRepository.getById(id);
        application.setStatus(status);
        applicationRepository.save(application);
        log.info("амжилттай болсон байх магадлалтай: {}", application.getStatus());
    }

    //applicaiton-аа сурагч нь өөрөө татгалзах
    public void updateApplicationStatusByStudent(Long id) {
        isApplicationExist(id);
        Application application = applicationRepository.getById(id);
        application.setStatus(Status.WITHDRAWN);
        applicationRepository.save(application);
        log.info("сурагч нь ажилттай өөрчилсөн байх магадлалтай: {}", application.getStatus());
    }

    //Компани нь өөр дээр нь ирсэн application хүсэлтүүдийг харах
    public List<ResponseApplicationsToOrganization> getPendingApplications(Long userId) {
        boolean isCompany = organizationRepository.existsByUserId(userId);
        if (!isCompany) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company user doesn't found");
        }
        //тухайн хэрэглэгчийн хариалагдах байгуулгын id-ийг авна
        Long organizationId = organizationRepository.findByUserId(userId).getOrganizationId();
        //тэр байгуулгын id-дээр хариалагдаж буй internshipPost-уудийг бүгдийг авна буюу тухайн байгуугын оруулсан заруудыг авна
        List<InternshipPost> internshipPosts = internshipPostRepository.findAllByOrganizationId(organizationId);
        //зар бүр дээр нь ажилна
        List<ResponseApplicationsToOrganization> responseFull = internshipPosts.stream()
                .map(post -> {
                    //нэг зар бүр дээр ирсэн хүсэлтийг авна
                    List<Application> eachPostsApplication = applicationRepository.findAllByInternshipPostId(post.getInternshipPostId());

                    List<ResponseApplication> listResponseApplication =
                            eachPostsApplication.stream()
                                    .map(each -> {
                                        Student student = studentRepository.findByUserId(each.getStudentId());
                                        return ResponseApplication.builder()
                                                .userId(student.getUserId())
                                                .firstName(student.getFirstName())
                                                .lastName(student.getLastName())
                                                .major(student.getMajor())
                                                .university(student.getUniversity())
                                                .courseYear(student.getCourseYear())
                                                .gpa(student.getGpa())
                                                .build();
                                    })
                                    .toList();

                    return ResponseApplicationsToOrganization.builder()
                            .internshipId(post.getInternshipPostId())
                            .title(post.getTitle())
                            .responseApplications(listResponseApplication)
                            .build();
                })
                .toList();
        return responseFull;
    }

    //helper function
    public void isApplicationExist(Long id) {
        boolean applicationExist = applicationRepository.existsById(id);
        if (!applicationExist) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "application doesn't found");
        }
    }
}
