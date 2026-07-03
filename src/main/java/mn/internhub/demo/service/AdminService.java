package mn.internhub.demo.service;

import mn.internhub.demo.api.dto.adminApi.RequestUpdate;
import mn.internhub.demo.api.dto.adminApi.ResponseUserDetail;
import mn.internhub.demo.data.Organizations;
import mn.internhub.demo.data.Student;
import mn.internhub.demo.data.Teacher;
import mn.internhub.demo.data.User;
import mn.internhub.demo.data.enums.Role;
import mn.internhub.demo.data.enums.UserStatus;
import mn.internhub.demo.repository.*;
import mn.internhub.demo.service.helperFunctions.gimmeId;
import mn.internhub.demo.service.helperFunctions.isItExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private OrganizationReviewRepository organizationReviewRepository;
    @Autowired
    private EvaluationRepository evaluationRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private isItExist isItExist;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private gimmeId gimmeId;

    ////систесийн бүх хэрэглэгчий авна
    public List<User> getAllUser(Long userId) {
        isItExist.isAdminByUserId(userId);
        return userRepository.findAll();
    }
    //сурагчийн дэлгэрэнгүй мэдээллйиг авна
    public ResponseUserDetail getAllUsersDetailById(Long adminId, Long userId) {
        isItExist.isAdminByUserId(adminId);
        isItExist.isUserExistByUserId(userId);
        UserStatus status = userRepository.findById(userId).orElseThrow(IllegalAccessError::new).getStatus();
        if(gimmeId.userIdToOrgId(userId) != null){
            Organizations organizations = organizationRepository.findById(gimmeId.userIdToOrgId(userId)).orElseThrow(IllegalAccessError::new);
            return ResponseUserDetail.builder()
                    .status(status)
                    .organizationId(organizations.getOrganizationId())
                    .organizationName(organizations.getOrganizationName())
                    .industry(organizations.getIndustry())
                    .address(organizations.getAddress())
                    .city(organizations.getCity())
                    .site(organizations.getSite())
                    .logoUrl(organizations.getLogoUrl())
                    .description(organizations.getDescription())
                    .isVerified(organizations.getIsVerified())
                    .build();
        }
        else if (gimmeId.userIdToStudentId(userId) != null){
            Student student = studentRepository.findById(gimmeId.userIdToStudentId(userId)).orElseThrow(IllegalAccessError::new);
            return ResponseUserDetail.builder()
                    .studentId(student.getStudentId())
                    .firstName(student.getFirstName())
                    .lastName(student.getLastName())
                    .major(student.getMajor())
                    .university(student.getUniversity())
                    .courseYear(student.getCourseYear())
                    .gpa(student.getGpa())
                    .skills(student.getSkills())
                    .languages(student.getLanguages())
                    .teacherId(student.getStudentId())
                    .build();
        }
        else if (gimmeId.userIdToTeacherId(userId) != null){
            Teacher teacher = teacherRepository.findById(gimmeId.userIdToTeacherId(userId)).orElseThrow(IllegalAccessError::new);
            return ResponseUserDetail.builder()
                    .teacherId(teacher.getTeacherId())
                    .phone(teacher.getPhone())
                    .build();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"эрх чинь бүрэхгүй байна");
        }
    }
    //хэрэглэгчдийн status-ийг өөрчилөх
    public UserStatus updateUserStatus(Long adminId, Long userId, RequestUpdate request) {
        isItExist.isAdminByUserId(adminId);
        isItExist.isUserExistByUserId(userId);
        boolean isAdmin = adminRepository.existsByUserId(userId);
        if (isAdmin){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"эрх чинь хүрэхгүй байна");
        }
        User user = userRepository.findById(userId).orElseThrow(IllegalAccessError::new);
        user.setStatus(request.status());
        userRepository.save(user);
        return user.getStatus();
    }
    //хэрэглэгчийн устгана
    public void deleteUser(Long adminId, Long userId) {
        isItExist.isAdminByUserId(adminId);
        isItExist.isUserExistByUserId(userId);
        boolean isAdmin = adminRepository.existsByUserId(userId);
        if (isAdmin){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"эрх чинь хүрэхгүй байна");
        }
        userRepository.delete(userRepository.findById(userId).orElseThrow(IllegalAccessError::new));
    }
}
