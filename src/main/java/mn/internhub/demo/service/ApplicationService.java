package mn.internhub.demo.service;

import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.api.dto.applicationApiDto.*;
import mn.internhub.demo.data.Application;
import mn.internhub.demo.data.InternshipPost;
import mn.internhub.demo.data.Student;
import mn.internhub.demo.data.enums.PaymentStatus;
import mn.internhub.demo.data.enums.Status;
import mn.internhub.demo.data.enums.UserStatus;
import mn.internhub.demo.repository.*;
import mn.internhub.demo.service.helperFunctions.gimmeId;
import mn.internhub.demo.service.helperFunctions.isItExist;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private gimmeId gimmeId;
    @Autowired
    private isItExist isItExist;
    @Autowired
    private TeacherRepository teacherRepository;


    //application хүсэлт үүсгэнэ post
    public Application createApplication(Long userId, Long postId, RequestApplication request) {
        boolean isPostExist = internshipPostRepository.existsById(postId);
        if (!isPostExist){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"зар чинь байхгүй байнаа хө");
        }
        boolean isBanned = userRepository.findById(userId).orElseThrow(IllegalAccessError::new).getStatus().equals(UserStatus.BANNED);
        if (isBanned){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"ээ чи бандуулсан байна шдээ");
        }
        Application application = Application.builder()
                .studentId(userId)
                .internshipPostId(postId)
                .status(Status.PENDING)
                .coverLetter(request.coverLetter())
                .paymentStatus(PaymentStatus.SUCCESS)
                .submittedAt(LocalDateTime.now())
                .build();
        return applicationRepository.save(application);
    }

    // эрхтэй хүний application-ийг илүү дэлгэрэнгүй харна үүнд нв сурагчийн дэлгэрэнгүй мэдээлэл орно.
    public ResponseApplicationDetail getApplicationDetail( Long userId,Long applicationId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found application"));
        if (studentRepository.existsByUserId(userId)){
            if (!application.getStudentId().equals(studentRepository.findByUserId(userId).getStudentId())){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"чинийх биш байнаа хө");
            }
        }
        else if (teacherRepository.existsByUserId(userId)) {
            Long teacherId = gimmeId.userIdToTeacherId(userId);
            //тухайн сурагч = appID-ийн хариулагдаж буй сурагч
            //тухайн сурагчийн багшийн id нь
            Long studentTeacherId = studentRepository.findById(applicationRepository.findById(applicationId).orElseThrow(IllegalAccessError::new).getStudentId()).orElseThrow(IllegalAccessError::new).getTeacherId();
            //тухайн сурагчийн хариуцаж буй багш нь id  нь байхгүй бол эсвэл тухайн хариуцаж байгаа багш id нь одоо хандаж дэлгэрэнгүй мэдээлэл авах гэж байгаа багшийн  Id-тай таарахгүй байвал энэ биелэн
            if (studentTeacherId != null || !studentTeacherId.equals(teacherId)) {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "чи багш нь мөн юм уу дөө");
            }
        }
        else if (organizationRepository.existsByUserId(userId)){
            Long orgId= gimmeId.userIdToOrgId(userId);
            boolean havePermission = internshipPostRepository.findById(applicationRepository.findById(applicationId).orElseThrow(IllegalAccessError::new).getInternshipPostId()).orElseThrow(IllegalAccessError::new).getOrganizationId().equals(orgId);
            if (!havePermission){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"чи биш байна шдээ хө");
            }
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"хэн юм бээ чи");
        }
        Student student = studentRepository.findByUserId(application.getStudentId());
        return ResponseApplicationDetail.builder()
                .appId(application.getApplicationId())
                .firstName(student.getFirstName())
                .lastName(student.getFirstName())
                .studentId(student.getStudentId())
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
    }

    //application id-гаар нь тухайн application-ий status-ийг өөрчилнө
    public void updateApplicationStatus(Long userID ,Long appId, RequestStatus status) {
        isApplicationExist(appId);
        Application application = applicationRepository.getReferenceById(appId);
        if (!gimmeId.userIdToOrgId(userID).equals(internshipPostRepository.findById(application.getInternshipPostId()).orElseThrow(IllegalAccessError::new).getOrganizationId())){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"not yourssss");
        }
        if (applicationRepository.findById(appId).orElseThrow(IllegalAccessError::new).getStatus().equals(Status.WITHDRAWN)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"чамд эрх чинь байхгүй байнаа ");
        }
        application.setStatus(status.status());
        applicationRepository.save(application);
    }

    //applicaiton-аа сурагч нь өөрөө татгалзах
    public void updateApplicationStatusByStudent(Long userId,Long appId) {
        isItExist.isStudentByUserId(userId);
        isApplicationExist(appId);
        Application application = applicationRepository.getReferenceById(appId);

        boolean hasPermission = studentRepository.findByUserId(userId).getStudentId().equals(application.getStudentId());
        log.info("чамдэрх {} ба чиний student ID{} ба чиний app-д хариу student id {}",hasPermission,studentRepository.findByUserId(userId).getStudentId(),application.getStudentId());
        if (!hasPermission){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"чинийх биш байнаа хө");
        }
        log.info("энд байна 5");
        application.setStatus(Status.WITHDRAWN);
        applicationRepository.save(application);
    }

    //Компани нь өөр дээр нь ирсэн application хүсэлтүүдийг харах
    public List<ResponseApplicationsToOrganization> getPendingApplications(Long userId) {
        isItExist.isOrgByUserId(userId);
        //тухайн хэрэглэгчийн хариалагдах байгуулгын id-ийг авна
        Long organizationId = organizationRepository.findByUserId(userId).getOrganizationId();
        //тэр байгуулгын id-дээр хариалагдаж буй internshipPost-уудийг бүгдийг авна буюу тухайн байгуугын оруулсан заруудыг авна
        List<InternshipPost> internshipPosts = internshipPostRepository.findAllByOrganizationId(organizationId);
        //зар бүр дээр нь ажилна
        return internshipPosts.stream()
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
                                                .status(each.getStatus())
                                                .coverLetter(each.getCoverLetter())
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
    }

    //helper function
    public void isApplicationExist(Long id) {
        boolean applicationExist = applicationRepository.existsById(id);
        if (!applicationExist) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "application doesn't found");
        }
    }
}
