package mn.internhub.demo.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.api.dto.studentApiDto.ResponseComment;
import mn.internhub.demo.api.dto.studentApiDto.ResponseStudentPro;
import mn.internhub.demo.api.dto.studentApiDto.UpdateProfileRequest;
import mn.internhub.demo.data.Application;
import mn.internhub.demo.data.FileEntity;
import mn.internhub.demo.data.Student;
import mn.internhub.demo.data.User;
import mn.internhub.demo.data.enums.ContentTypes;
import mn.internhub.demo.repository.FileEntityRepository;
import mn.internhub.demo.service.FileEntityService;
import mn.internhub.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentApi {
    @Autowired
    private StudentService studentService;
    @Autowired
    private FileEntityRepository fileRepository;
    @Autowired
    private FileEntityService fileService;

    //өөрийн мэдээллийн дэлгэрэнгүйг авах
    @GetMapping("/profile")
    public ResponseStudentPro getProfile(@AuthenticationPrincipal User user){
        return studentService.getProfile(user.getUserId());
    }
    //өөрийн мэдээллийг дэлгэрэнгүй үүсгэх/өөрлчөх
    @PutMapping("/profile")
    public Student updateProfile(@AuthenticationPrincipal User user, @RequestBody UpdateProfileRequest updateRequest){
        if (updateRequest == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"null");
        }
        return studentService.updateProfile(user.getUserId(), updateRequest);
    }
    @PutMapping("/profile/cv")
    public void updateCV(@AuthenticationPrincipal User user,@RequestParam("file") MultipartFile file  ){
        studentService.updateCV( user.getUserId(),file);
    }
    //get userCV file
    @GetMapping("/profile/cv")
    public FileEntity getCv(@AuthenticationPrincipal User user){
        return fileService.getCv(user);
    }
    //сурагчийн мэдээллийг багш, ажил олгогч нь дэлгэрэнгүй харах ингэхдээ тухайн сурагчийн student_id нь авах байдлаар
    @GetMapping("/{studentId}")
    public Student getStudentProfile(@AuthenticationPrincipal User user, @PathVariable Long studentId){
        return studentService.getStudentProfile(studentId);
    }
    @GetMapping("/avg")
    public float getAvgStudentScore(@AuthenticationPrincipal User user){
        return studentService.getAvgStudentScore(user.getUserId());
    }
    @GetMapping("/comments/{studentId}")
    public List<ResponseComment> getStudentComment(@AuthenticationPrincipal User user, @PathVariable Long studentId){
        return studentService.getStudentComment(studentId);
    }
    //тухайн сурагч нь өөрийн явуулсан анкетүүдийн мэдээллийг авах
    @GetMapping("/application")
    public List<Application> getApplications(@AuthenticationPrincipal User user){
        return studentService.getApplications(user);
    }
    //туханй сурагч нь өөрийн явуулсан хүсэлтийг дэлгэрэнгүйг харах
    @GetMapping("/application/{id}")
    public Application getApplicationDetail(@AuthenticationPrincipal User user,@PathVariable Long id){
        return studentService.getApplicationDetail(id);
    }
    //Бүртгэлтэй буй нийт сурагчийн тоо
    @GetMapping("/all")
    public long getAllStudentNum(){
        return studentService.getAllStudentNum();
    }
    //Post user profile image
    @PostMapping("/profile/img")
    public ResponseEntity<String> createProfileImage(@AuthenticationPrincipal User user, @RequestParam("file") MultipartFile file){
        return fileService.createProfileImg(user.getUserId(), file, ContentTypes.PROFILE);
    }
    //өөрйин profile зургыг авах
    @GetMapping("/profile/img")
    public FileEntity getProfileImg(@AuthenticationPrincipal User user){
        return fileService.getProfileImg(user.getUserId(), ContentTypes.PROFILE);
    }
    //update own profile
    @PutMapping("/profile/img")
    public FileEntity updateProfiileImg(@AuthenticationPrincipal User user, @RequestParam("file") MultipartFile file){
        return fileService.updateProfileImg(user.getUserId(), file, ContentTypes.PROFILE);
    }



}
