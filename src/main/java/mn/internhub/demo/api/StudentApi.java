package mn.internhub.demo.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.api.dto.AuthResponse;
import mn.internhub.demo.api.dto.UpdateProfileRequest;
import mn.internhub.demo.data.Student;
import mn.internhub.demo.data.User;
import mn.internhub.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentApi {
    @Autowired
    private StudentService studentService;
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
}
