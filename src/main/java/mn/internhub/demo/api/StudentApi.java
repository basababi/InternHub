package mn.internhub.demo.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.api.dto.studentApiDto.UpdateProfileRequest;
import mn.internhub.demo.data.Application;
import mn.internhub.demo.data.Student;
import mn.internhub.demo.data.User;
import mn.internhub.demo.repository.StudentRepository;
import mn.internhub.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentApi {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;

    //өөрийн мэдээллийн дэлгэрэнгүйг авах
    @GetMapping("/profile")
    public Student getProfile(@AuthenticationPrincipal User user){
        log.info("байгууллагын өгөгдлийг авсан");
        return studentService.getProfile(user.getUserId());
    }
    //өөрийн мэдээллийг дэлгэрэнгүй үүсгэх/өөрлчөх
    @PutMapping("/profile")
    public Student updateProfile(@AuthenticationPrincipal User user, @RequestBody UpdateProfileRequest updateRequest){
        log.info("энэ хүртэл ирлээ");
        if (updateRequest == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"null");
        }
        log.info("ямартай ч зөв хүслэт авсан");
        return studentService.updateProfile(user.getUserId(), updateRequest);
    }
    //сурагчийн мэдээллийг багш, ажил олгогч нь дэлгэрэнгүй харах ингэхдээ тухайн сурагчийн student_id нь авах байдлаар
    @GetMapping("/{id}")
    public Student getStudentProfile(@AuthenticationPrincipal User user, @PathVariable Long id){
        return studentService.getStudentProfile(user.getUserId(), id);
    }
    //тухайн сурагч нь өөрийн явуулсан анкетүүдийн мэдээллийг авах
    @GetMapping("/application")
    public List<Application> getApplications(@AuthenticationPrincipal User user){
        return studentService.getApplications(user);
    }
    //туханй сурагч нь өөрийн явуулсан хүсэлтийг дэлгэрэнгүйг харах
    @GetMapping("/application/{id}")
    public Application getApplicationDetail(@AuthenticationPrincipal User user,@PathVariable Long applicationId){
        return studentService.getApplicationDetail(applicationId);
    }


}
